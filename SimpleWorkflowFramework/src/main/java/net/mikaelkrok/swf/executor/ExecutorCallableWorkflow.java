/**
 * 
 */
package net.mikaelkrok.swf.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.mikaelkrok.swf.exception.StepAlreadyExistingException;
import net.mikaelkrok.swf.exception.StepBadPreviousException;
import net.mikaelkrok.swf.exception.StepPreviousNotExisitingException;
import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.Step;
import net.mikaelkrok.swf.workflow.Workflow;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.log4j.Logger;

/**
 * @author Mikael KROK
 * 
 *         test - 25 nov. 2012
 * 
 */
public class ExecutorCallableWorkflow<O extends Object, M extends Milestone<O>, S extends Step<M, O>> implements Workflow<O, M, S> {

	private static org.apache.log4j.Logger LOGGER = Logger.getLogger(ExecutorCallableWorkflow.class);

	private static ExecutorService executor = new StripedExecutorService();

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
	private ListMultimap<Integer, Integer> workflow = ArrayListMultimap.create();

	/**
	 * Step is final
	 */
	private List<Integer> finalStep = new ArrayList<Integer>();

	private Map<Integer, S> steps = new HashMap<Integer, S>();

	/**
	 * save the future to extract the result when needed
	 */
	private Map<Integer, Future<M>> futures = new HashMap<Integer, Future<M>>();

	public void addStep(S step) throws StepAlreadyExistingException, StepPreviousNotExisitingException, StepBadPreviousException {
		if (!(step.getStepId() > step.getGetPreviousStepId())) {
			// throw an exception be cause a step cannot have an id > at its
			// previous step
			throw new StepBadPreviousException();
		}

		// check that this step id is taken
		if (steps.containsKey(step.getStepId())) {
			throw new StepAlreadyExistingException();
		}

		if (!steps.containsKey(step.getGetPreviousStepId()) && step.getGetPreviousStepId() != 0) {
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

		LOGGER.info("****************************************************************");
		LOGGER.info("                      Starting workflow engine");
		LOGGER.info("****************************************************************");
		try {
			SortedSet<Integer> sortedSet = new TreeSet<Integer>(workflow.keySet());
			// first step
			S callableStep1 = steps.get(1);
			executeStep(callableStep1);

			for (Integer stepNumber : sortedSet) {
				// Execute step
				S callableStep = steps.get(stepNumber);
				if (stepNumber != 1) {
					executeStep(callableStep);
				}
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			LOGGER.info("****************************************************************");
			LOGGER.info("                      Shutting down engine");
			LOGGER.info("****************************************************************");
			executor.shutdown();
		}
		return milestone;
	}

	/**
	 * 
	 * @param milestone
	 * @param callableStep
	 * @return success or not
	 * @throws CloneNotSupportedException
	 * @throws InterruptedException
	 */
	private boolean doExecuteStep(S callableStep) throws CloneNotSupportedException, InterruptedException {
		if (callableStep.getMilestone() == null) {
			LOGGER.info("ExecutorCallableWorkflow.doExecuteStep() milestone is null in parameter for step " + callableStep.getStepId());
			return false;
		}
		try {
			@SuppressWarnings("unchecked")
			M milestoneCopy = (M) callableStep.getMilestone().clone();
			callableStep.setMilestone(milestoneCopy);
			if (!callableStep.hasBeenExecuted()) {
				Map<Integer, Future<M>> m = new HashMap<Integer, Future<M>>();
				List<Future<M>> invokeAll = executor.invokeAll(Arrays.asList(callableStep));
				Integer i = 0;
				for (Future<M> future : invokeAll) {
					m.put(i++, future);
				}
				futures.putAll(m);
			}
		} catch (ClassCastException e) {
			// Let it fail policy (Equivalent to a very complicated instanceof
			// for generic)
		}
		return true;
	}

	/**
	 * 
	 * @param callableStep
	 * @return
	 * @throws CloneNotSupportedException
	 * @throws InterruptedException
	 */
	private void executeStep(S callableStep) throws CloneNotSupportedException, InterruptedException {
		Integer stepId = callableStep.getStepId();
		LOGGER.info("* Executing step " + stepId);

		callableStep.setStripe(stepId);
		doExecuteStep(callableStep);

		LOGGER.info("** Executing substeps for step " + stepId);
		// Then execute sub steps
		List<Integer> listStep = workflow.get(stepId);

		for (Integer subStepNumber : listStep) {
			LOGGER.info("**** Executing substep " + subStepNumber);
			S subCallableStep = steps.get(subStepNumber);
			try {
				LOGGER.info("ExecutorCallableWorkflow.executeSubSteps()" + callableStep.getMilestone());
				subCallableStep.setStripe(stepId);
				subCallableStep.setMilestone((M) callableStep.getMilestone().clone());
				doExecuteStep(subCallableStep);

			} catch (ClassCastException e) {
				// Let it fail policy (Equivalent to a very complicated
				// instanceof for generic)
			}
		}
		// return futures.get(stepId);
	}

	public List<S> getFinalSteps() {
		List<S> stepsList = new ArrayList<S>();
		for (Integer subStepNumber : finalStep) {
			stepsList.add(steps.get(subStepNumber));
		}
		return stepsList;
	}
}
