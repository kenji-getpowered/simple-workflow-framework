package net.mikaelkrok.smf.string;

import net.mikaelkrok.smf.Milestone;
import net.mikaelkrok.smf.exception.StepWithNullMilestoneException;
import net.mikaelkrok.smf.executor.CallableStep;

/**
 * 
 * @author Mikael KROK 
 * 
 *         23 nov. 2012
 * 
 *  render the {@link Milestone<String>} to capital letters
 * 
 */
public class StringStepCapitalizer<V extends Milestone<String>> extends
		CallableStep<String, V> {

	public StringStepCapitalizer(int stepId, int previousStepId,
			boolean finalStep) {
		super(stepId, previousStepId, finalStep);
	}

	public StringStepCapitalizer(int stepId, boolean finalStep) {
		super(stepId, finalStep);
	}

	public StringStepCapitalizer(int stepId, int previousStepId) {
		super(stepId, previousStepId, false);
	}

	public V call() throws Exception {
		if(milestone == null ) throw new StepWithNullMilestoneException();
		milestone.setValue(milestone.getValue().toUpperCase());
		System.out.println(" Result after step " + this.getClass().getName()
				+ "  " + milestone.getValue());
		return milestone;
	}

}
