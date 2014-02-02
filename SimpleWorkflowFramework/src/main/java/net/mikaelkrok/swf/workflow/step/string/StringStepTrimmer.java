package net.mikaelkrok.swf.workflow.step.string;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.mikaelkrok.swf.SimpleWorkflowFramework;
import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.step.StepImpl;

/**
 * 
 * @author Mikael KROK
 * 
 *         23 nov. 2012
 * 
 *         This steps trims white space n the word
 * 
 */
@Service("stringStepTrimmer")
@Scope("prototype")
public class StringStepTrimmer<V extends Milestone<String>> extends
StepImpl<String, V> {

	private static org.apache.log4j.Logger LOGGER = Logger.getLogger(StringStepTrimmer.class);
	
	public V call() throws Exception {
		milestone.setValue(milestone.getValue().trim());
		LOGGER.info(" Result after step " + this.getClass().getName()
				+ "  +" + milestone.getValue() + "+");
		return milestone;
	}

}
