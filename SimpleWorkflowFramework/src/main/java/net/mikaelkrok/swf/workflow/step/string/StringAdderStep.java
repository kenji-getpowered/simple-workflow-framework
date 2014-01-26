/**
 * 
 */
package net.mikaelkrok.swf.workflow.step.string;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.step.StepImpl;

/**
 * @author Mikael KROK 
 * 
 *         19 déc. 2012
 * 
 */
@Service("stringAdderStep")
@Scope("prototype")
public class StringAdderStep<V extends Milestone<String>> extends
		StepImpl<String, V> {

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
