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
 *         This steps trims white space n the word
 * 
 */
public class StringStepTrimmer<V extends Milestone<String>, U extends Milestone<String>> extends
CallableStep<V, String, U, String> {
	
	public StringStepTrimmer(int stepId, int previousStepId, boolean finalStep) {
		super(stepId, previousStepId, finalStep);
	}

	public StringStepTrimmer(int stepId, boolean finalStep) {
		super(stepId, finalStep);
	}

	public StringStepTrimmer(int stepId, int previousStepId) {
		super(stepId, previousStepId, false);
	}

	public Boolean call() throws Exception {
		if(milestone == null ) throw new StepWithNullMilestoneException();
		milestone.setValue(milestone.getValue().trim());
		System.out.println(" Result after step " + this.getClass().getName()
				+ "  +" + milestone.getValue() + "+");
		return  true;
	}

}
