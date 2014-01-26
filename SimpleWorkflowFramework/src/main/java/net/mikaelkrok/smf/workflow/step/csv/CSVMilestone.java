package net.mikaelkrok.smf.workflow.step.csv;

import java.io.File;

import net.mikaelkrok.smf.workflow.Milestone;
import net.mikaelkrok.smf.workflow.step.StepImpl;

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
