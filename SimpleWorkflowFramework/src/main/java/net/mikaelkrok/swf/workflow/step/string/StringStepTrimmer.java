package net.mikaelkrok.swf.workflow.step.string;

import org.springframework.stereotype.Service;

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
@Service
public class StringStepTrimmer<V extends Milestone<String>> extends
StepImpl<String, V> {

	public V call() throws Exception {
		milestone.setValue(milestone.getValue().trim());
		System.out.println(" Result after step " + this.getClass().getName()
				+ "  +" + milestone.getValue() + "+");
		return milestone;
	}

}
