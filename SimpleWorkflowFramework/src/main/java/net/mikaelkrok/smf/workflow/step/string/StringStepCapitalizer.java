package net.mikaelkrok.smf.workflow.step.string;

import org.springframework.stereotype.Service;

import net.mikaelkrok.smf.exception.StepWithNullMilestoneException;
import net.mikaelkrok.smf.workflow.Milestone;
import net.mikaelkrok.smf.workflow.step.StepImpl;

/**
 * 
 * @author Mikael KROK 
 * 
 *         23 nov. 2012
 * 
 *  render the {@link Milestone<String>} to capital letters
 * 
 */
@Service
public class StringStepCapitalizer<V extends Milestone<String>> extends
		StepImpl<String, V> {

	public V call() throws Exception {
		if(milestone == null ) throw new StepWithNullMilestoneException();
		milestone.setValue(milestone.getValue().toUpperCase());
		System.out.println(" Result after step " + this.getClass().getName()
				+ "  " + milestone.getValue());
		return milestone;
	}

}
