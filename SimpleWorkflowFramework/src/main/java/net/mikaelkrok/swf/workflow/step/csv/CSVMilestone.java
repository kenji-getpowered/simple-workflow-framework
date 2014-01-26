package net.mikaelkrok.swf.workflow.step.csv;

import java.io.File;

import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.step.StepImpl;

/**
 * @author Mikael KROK
 * 
 *         17 janv. 2013
 * 
 */
public abstract class CSVMilestone<M extends Milestone<File>> extends
		StepImpl<File, M> {

	public M call() throws Exception {
		return milestone;
	}
}
