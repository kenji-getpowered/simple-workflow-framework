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
public class CallableStep<
	T extends Milestone<A>, A extends Object , 
	U extends Milestone<B> ,B extends Object> 
		implements		Callable<Boolean>, Step<T, A, U, B> {

	protected int stepId;

	protected int previousStepId;

	protected boolean finalStep;

	protected boolean executed;

	protected T milestone;

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

	public Boolean call() throws Exception {
		System.out.println("CallableStep.call() ");
		if (milestone != null) {
			milestone.getValue();
		}
		return true;
	}

	/**
	 * in this implementation the method execute allows to pass a parameter to
	 * the thread
	 * 
	 */
	public void setMilestone(T milestone) {
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

	public U getMilestone() {
		return (U) milestone;
	}

}
