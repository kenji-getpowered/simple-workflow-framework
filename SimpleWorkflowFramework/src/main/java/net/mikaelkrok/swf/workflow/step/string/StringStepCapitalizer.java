package net.mikaelkrok.swf.workflow.step.string;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.mikaelkrok.swf.exception.StepWithNullMilestoneException;
import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.step.StepImpl;

/**
 * 
 * @author Mikael KROK 
 * 
 *         23 nov. 2012
 * 
 *  render the {@link Milestone<String>} to capital letters
 * 
 */
@Service("stringStepCapitalizer")
@Scope("prototype")
public class StringStepCapitalizer<V extends Milestone<String>> extends
		StepImpl<String, V> {

	public V call() throws Exception {
		if(milestone == null ) throw new StepWithNullMilestoneException();
		milestone.setValue(milestone.getValue().toUpperCase());
		LOGGER.info(" Result after step " + this.getClass().getName()
				+ "  " + milestone.getValue());
		return milestone;
	}

}
