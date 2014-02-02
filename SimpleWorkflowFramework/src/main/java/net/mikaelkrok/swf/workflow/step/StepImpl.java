/**
 * 
 */
package net.mikaelkrok.swf.workflow.step;

import org.apache.log4j.Logger;

import net.mikaelkrok.swf.SimpleWorkflowFramework;
import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.Step;

/**
 * @author Mikael KROK
 * 
 *         25 nov. 2012
 * 
 *         Basic callable step.
 * 
 *         The callable step implements the interface Callable to be executed by
 *         an executor and Step to be able to accept a parameter
 * 
 */
public class StepImpl<O extends Object, M extends Milestone<O>> implements Step<M, O> {

	private static org.apache.log4j.Logger LOGGER = Logger.getLogger(StepImpl.class);

	protected int stepId;

	protected int previousStepId;

	protected boolean finalStep;

	protected boolean executed;

	protected M milestone;

	public M call() throws Exception {
		LOGGER.info("CallableStep.call() ");
		if (milestone != null) {
			milestone.getValue();
		}
		return milestone;
	}

	/**
	 * in this implementation the method execute allows to pass a parameter to
	 * the thread
	 * 
	 */
	public void setMilestone(M milestone) {
		this.milestone = milestone;
	}

	public Integer getStepId() {
		return this.stepId;
	}

	public Integer getGetPreviousStepId() {
		return this.previousStepId;
	}

	public Boolean isFinalStep() {
		return this.finalStep;
	}

	public Boolean hasBeenExecuted() {
		return this.executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public M getMilestone() {
		return milestone;
	}

	@Override
	public void init(int stepId, int previousStepId, boolean finalStep) {
		this.stepId = stepId;
		this.previousStepId = previousStepId;
		this.finalStep = finalStep;
	}

	private Object callSequence;
	
	@Override
	public Object getStripe() {
		return callSequence;
	}

	@Override
	public void setStripe(Object callSequence) {
		this.callSequence = callSequence;
		
	}

}
