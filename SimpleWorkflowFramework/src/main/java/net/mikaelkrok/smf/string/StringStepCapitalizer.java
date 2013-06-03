package net.mikaelkrok.smf.string;

import net.mikaelkrok.smf.Milestone;
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
		milestone.setValue(milestone.getValue().toUpperCase());
		System.out.println(" Result after step " + this.getClass().getName()
				+ "  " + milestone.getValue());
		return milestone;
	}

}
