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

import net.mikaelkrok.smf.Milestone;
import net.mikaelkrok.smf.Workflow;
import net.mikaelkrok.smf.exception.StepAlreadyExistingException;
import net.mikaelkrok.smf.exception.StepBadPreviousException;
import net.mikaelkrok.smf.exception.StepPreviousNotExisitingException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * @author Mikael KROK
 * 
 *         test - 25 nov. 2012
 * 
 */
public class ExecutorCallableWorkflow<U extends Object, V extends Milestone<U>>
		implements Workflow<U, Milestone<U>, CallableStep<U, V>> {

	ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Implementation of diagram of step precedence This is a list of keys,
	 * linked to other keys.
	 * 
	 * Step 2 requires Step 1 Step 5 requires Step 1
	 * 
	 * Step 6 requires Step 2 Step 3 requires Step 2
	 * 
	 * 1 : 2, 5 2 : 6, 3
	 * 
	 */
	private ListMultimap<Integer, Integer> workflow = ArrayListMultimap
			.create();

	/**
	 * Step is final
	 */
	private List<Integer> finalStep = new ArrayList<Integer>();

	private Map<Integer, CallableStep<U, V>> steps = new HashMap<Integer, CallableStep<U, V>>();

	/**
	 * save the future to extract the result when needed
	 */
	private Map<Integer, Future<V>> futures = new HashMap<Integer, Future<V>>();

	List<CallableStep> list = new ArrayList<CallableStep>();

	public void addStep(CallableStep<U, V> step)
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

	public Milestone<U> run(Milestone<U> milestone) {
		System.out
				.println("****************************************************************");
		System.out.println("                      Starting workflow engine");
		System.out
				.println("****************************************************************");
		try {
			SortedSet<Integer> sortedSet = new TreeSet<Integer>(
					workflow.keySet());
			for (Integer stepNumber : sortedSet) {
				// Execute step
				CallableStep callableStep = steps.get(stepNumber);
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
	private void executeSubSteps(CallableStep<U, V> callableStep)
			throws CloneNotSupportedException, InterruptedException,
			ExecutionException {
		System.out.println("** Executing substeps for step "
				+ callableStep.getStepId());
		// Then execute sub steps
		List<Integer> listStep = workflow.get(callableStep.getStepId());

		// prepare its parameter
		// V milestone2 = getMilestone(callableStep);

		for (Integer subStepNumber : listStep) {
			System.out.println("**** Executing substep " + subStepNumber);
			CallableStep subCallableStep = steps.get(subStepNumber);
			subCallableStep.setMilestone((Milestone) callableStep
					.getMilestone().clone());
		}
	}

	/**
	 * 
	 * @param milestone
	 * @param callableStep
	 * @return success or not
	 * @throws CloneNotSupportedException
	 */
	private boolean doExecuteStep(CallableStep<U, V> callableStep)
			throws CloneNotSupportedException {
		if (callableStep.getMilestone() == null) {
			System.out
					.println("ExecutorCallableWorkflow.doExecuteStep() milestone is null in parameter for step "
							+ callableStep.getStepId());
			return false;
		}
		V milestoneCopy = (V) callableStep.getMilestone().clone();
		callableStep.setMilestone(milestoneCopy);
		if (!callableStep.hasBeenExecuted()) {
			futures.put(callableStep.getStepId(), executor.submit(callableStep));
		}
		return true;
	}

	/**
	 * 
	 * @param callableStep
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private Future<V> executeStep(CallableStep<U, V> callableStep)
			throws CloneNotSupportedException {
		System.out.println("* Executing step " + callableStep.getStepId());
		doExecuteStep(callableStep);
		return futures.get(callableStep.getStepId());
	}

	public List<CallableStep> getFinalSteps() {
		List<CallableStep> stepsList = new ArrayList<CallableStep>();
		for (Integer subStepNumber : finalStep) {
			stepsList.add(steps.get(subStepNumber));
		}
		return stepsList;
	}
}
