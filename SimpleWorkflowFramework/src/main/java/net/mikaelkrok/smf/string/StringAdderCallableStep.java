/**
 * 
 */
package net.mikaelkrok.smf.string;

import net.mikaelkrok.smf.Milestone;
import net.mikaelkrok.smf.executor.CallableStep;

/**
 * @author Mikael KROK 
 * 
 *         19 déc. 2012
 * 
 */
public class StringAdderCallableStep<V extends Milestone<String>> extends
		CallableStep<String, V> {

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
	public V call() throws Exception {
		if (!hasBeenExecuted()) {
			synchronized (this) {
				if (milestone != null) {
					String newValue = milestone.getValue() + "+";
					System.out.println("StringAdderCallableStep.call()- "
							+ stepId + " - " + newValue);
					milestone.setValue(newValue);
				} else {
					System.out.println(" milestone is null");
				}
				setExecuted(true);
				return milestone;
			}
		}
		System.out.println("StringAdderCallableStep.call() " + stepId
				+ " - has already been executed");
		return milestone;
	}
}
