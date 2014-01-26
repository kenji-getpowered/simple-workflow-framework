/**
 * 
 */
package net.mikaelkrok.smf.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.mikaelkrok.smf.exception.StepAlreadyExistingException;
import net.mikaelkrok.smf.exception.StepBadPreviousException;
import net.mikaelkrok.smf.exception.StepPreviousNotExisitingException;
import net.mikaelkrok.smf.workflow.Milestone;
import net.mikaelkrok.smf.workflow.Step;
import net.mikaelkrok.smf.workflow.Workflow;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * @author Mikael KROK
 * 
 *         test - 25 nov. 2012
 * 
 */
public class ExecutorCallableWorkflow<O extends Object, M extends Milestone<O>, S extends Step<M, O>> 
		implements Workflow<O, M, S> {

	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * <pre>
	 * Step 2 requires Step 1 
	 * Step 5 requires Step 1
	 * Step 6 requires Step 2 
	 * Step 3 requires Step 2
	 * Step 7 requires Step 6
	 * =>
	 * 1 : 2,5 
	 * 2 : 6,3 
	 * 6 : 7
	 * </pre>
	 * 
	 */
	private ListMultimap<Integer, Integer> workflow = ArrayListMultimap
			.create();

	/**
	 * Step is final
	 */
	private List<Integer> finalStep = new ArrayList<Integer>();

	private Map<Integer, S> steps = new HashMap<Integer, S>();

	/**
	 * save the future to extract the result when needed
	 */
	private Map<Integer, Future<M>> futures = new HashMap<Integer, Future<M>>();

	public void addStep(S step)
			throws StepAlreadyExistingException,
			StepPreviousNotExisitingException, StepBadPreviousException {
		if (!(step.getStepId() > step.getGetPreviousStepId())) {
			// throw an exception be cause a step cannot have an id > at its
			// previous step
			throw new StepBadPreviousException();
		}

		// check that this step id is taken
		if (steps.containsKey(step.getStepId())) {
			throw new StepAlreadyExistingException();
		}

		if (!steps.containsKey(step.getGetPreviousStepId())
				&& step.getGetPreviousStepId() != 0) {
			// throw an exception because the step indirect to a not existing
			// steps
			throw new StepPreviousNotExisitingException();
		}

		steps.put(step.getStepId(), step);
		/*
		 * adding the precedence constraint If 0, there is no precedence
		 * constraint
		 */
		if (step.getGetPreviousStepId() > 0) {
			workflow.put(step.getGetPreviousStepId(), step.getStepId());
		}

		if (step.isFinalStep()) {
			finalStep.add(step.getStepId());
		}

	}

	public M run(M milestone) {
		

		System.out
				.println("****************************************************************");
		System.out.println("                      Starting workflow engine");
		System.out
				.println("****************************************************************");
		try {
			SortedSet<Integer> sortedSet = new TreeSet<Integer>(
					workflow.keySet());
			// first step
			S callableStep1 = steps.get(1);
			executeStep(callableStep1);
			executeSubSteps(callableStep1);
			
			for (Integer stepNumber : sortedSet) {
				// Execute step
				S callableStep = steps.get(stepNumber);
				if (stepNumber == 1) {
					callableStep.setMilestone(milestone);
				}
				executeStep(callableStep);
				executeSubSteps(callableStep);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} finally {
			System.out
					.println("****************************************************************");
			System.out.println("                      Shutting down engine");
			System.out
					.println("****************************************************************");
			executor.shutdown();
		}
		return milestone;
	}

	/**
	 * @param future
	 * @param callableStep
	 * @param listStep
	 * @param milestone2
	 * @param stepNumber
	 * @return
	 * @throws CloneNotSupportedException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	private void executeSubSteps(S callableStep)
			throws CloneNotSupportedException, InterruptedException,
			ExecutionException {
		System.out.println("** Executing substeps for step "
				+ callableStep.getStepId());
		// Then execute sub steps
		List<Integer> listStep = workflow.get(callableStep.getStepId());
		
		for (Integer subStepNumber : listStep) {
			System.out.println("**** Executing substep " + subStepNumber);
			S  subCallableStep = steps.get(subStepNumber);
		    try {
		    	subCallableStep.setMilestone((M) callableStep
		    			.getMilestone().clone());
		    } catch (ClassCastException e) {
		    	// Let it fail policy (Equivalent to a very complicated instanceof for generic)
		    }
		}
	}

	/**
	 * 
	 * @param milestone
	 * @param callableStep
	 * @return success or not
	 * @throws CloneNotSupportedException
	 */
	private boolean doExecuteStep(S callableStep)
			throws CloneNotSupportedException {
		if (callableStep.getMilestone() == null) {
			System.out
					.println("ExecutorCallableWorkflow.doExecuteStep() milestone is null in parameter for step "
							+ callableStep.getStepId());
			return false;
		}
	    try {
	    	@SuppressWarnings("unchecked")
			M milestoneCopy = (M) callableStep.getMilestone().clone();
	    	callableStep.setMilestone(milestoneCopy);
	    	if (!callableStep.hasBeenExecuted()) {
	    		futures.put(callableStep.getStepId(), executor.submit(callableStep));
	    	}
	    } catch (ClassCastException e) {
	    	// Let it fail policy (Equivalent to a very complicated instanceof for generic)
	    }
		return true;
	}

	/**
	 * 
	 * @param callableStep
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private Future<M> executeStep(S callableStep)
			throws CloneNotSupportedException {
		System.out.println("* Executing step " + callableStep.getStepId());
		doExecuteStep(callableStep);
		return futures.get(callableStep.getStepId());
	}

	public List<S> getFinalSteps() {
		List<S> stepsList = new ArrayList<S>();
		for (Integer subStepNumber : finalStep) {
			stepsList.add(steps.get(subStepNumber));
		}
		return stepsList;
	}
}
