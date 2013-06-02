/**
 * 
 */
package net.mikaelkrok.smf.executor;

import java.util.concurrent.Callable;

import net.mikaelkrok.smf.Milestone;
import net.mikaelkrok.smf.Step;

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
public class CallableStep<U extends Object, V extends Milestone<U>> implements
		Callable<V>, Step<V, U> {

	protected int stepId;

	protected int previousStepId;

	protected boolean finalStep;

	protected boolean executed;

	protected V milestone;

	public CallableStep(int stepId, int previousStepId, boolean finalStep) {
		super();
		this.stepId = stepId;
		this.previousStepId = previousStepId;
		this.finalStep = finalStep;
	}

	public CallableStep(int stepId, boolean finalStep) {
		super();
		this.stepId = stepId;
		this.previousStepId = 0;
		this.finalStep = finalStep;
	}

	public CallableStep(int stepId, int previousStepId) {
		this(stepId, previousStepId, false);
	}

	public V call() throws Exception {
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
	public void setMilestone(V milestone) {
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

	public V getMilestone() {
		return milestone;
	}

}
