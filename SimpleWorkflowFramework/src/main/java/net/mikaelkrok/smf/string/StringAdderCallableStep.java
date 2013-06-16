/**
 * 
 */
package net.mikaelkrok.smf.string;

import net.mikaelkrok.smf.Milestone;
import net.mikaelkrok.smf.exception.StepWithNullMilestoneException;
import net.mikaelkrok.smf.executor.CallableStep;

/**
 * @author Mikael KROK 
 * 
 *         19 déc. 2012
 * 
 */
public class StringAdderCallableStep<V extends Milestone<String>, U extends Milestone<String>> extends
		CallableStep<V, String, U, String> {

	public StringAdderCallableStep(int stepId, int previousStepId,
			boolean finalStep) {
		super(stepId, previousStepId, finalStep);
	}

	public StringAdderCallableStep(int stepId, boolean finalStep) {
		super(stepId, finalStep);
	}

	public StringAdderCallableStep(int stepId, int previousStepId) {
		super(stepId, previousStepId, false);
	}

	@Override
	public Boolean call() throws Exception {
		if(milestone == null ) throw new StepWithNullMilestoneException();
		if (!hasBeenExecuted()) {
			synchronized (this) {
				if (milestone != null) {
					String newValue = milestone.getValue() + "+";
					System.out.println("StringAdderCallableStep.call()- "
							+ stepId + " - " + newValue);
					milestone.setValue(newValue);
					setExecuted(true);
				} else {
					System.out.println(" milestone is null");
				}
				return  true;
			}
		}
		System.out.println("StringAdderCallableStep.call() " + stepId
				+ " - has already been executed");
		return  true;
	}
}
