package net.mikaelkrok.smf;

import java.io.File;

import net.mikaelkrok.smf.executor.CallableStep;

/**
 * @author Mikael KROK 884002
 * 
 * 17 janv. 2013
 *
 */
public abstract class CSVStepRemove <V extends Milestone<File>> extends CallableStep<File, V>  {
    
    public CSVStepRemove(int stepId, int previousStepId, boolean finalStep) {
	super(stepId, previousStepId, finalStep);
    }

    public CSVStepRemove(int stepId, boolean finalStep) {
	super(stepId, finalStep);
    }

    public CSVStepRemove(int stepId, int previousStepId) {
	super(stepId, previousStepId, false);
    }
    
    public V call() throws Exception {
		return milestone;
	
//	milestone.setValue(milestone.getValue().trim());
//	System.out.println(" Result after step "+ this.getClass().getName()+"  +"+  milestone.getValue()+"+");
//	return milestone;
    }
    
    

}
