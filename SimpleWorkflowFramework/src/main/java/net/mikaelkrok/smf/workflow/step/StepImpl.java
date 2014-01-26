/**
 * 
 */
package net.mikaelkrok.smf.workflow.step;

import net.mikaelkrok.smf.workflow.Milestone;
import net.mikaelkrok.smf.workflow.Step;

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

	protected int stepId;

	protected int previousStepId;

	protected boolean finalStep;

	protected boolean executed;

	protected M milestone;

	public M call() throws Exception {
		System.out.println("CallableStep.call() ");
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

}
