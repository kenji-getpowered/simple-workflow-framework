/**
 * 
 */
package net.mikaelkrok.swf.workflow.milestone.string;

import net.mikaelkrok.swf.workflow.Milestone;

/**
 * @author Mikael KROK 
 * 
 *         21 nov. 2012
 * 
 */
public class StringMilestoneImpl implements Milestone<String> {

	private String myString;

	public String getValue() {
		return myString;
	}

	public void setValue(String myString) {
		this.myString = myString;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		StringMilestoneImpl impl = new StringMilestoneImpl();
		impl.setValue(myString);
		return impl;
	}

}
