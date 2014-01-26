/**
 * 
 */
package net.mikaelkrok.swf.workflow;

import java.io.Serializable;
import java.util.concurrent.Callable;


/**
 * @author Mikael KROK 
 * 
 * 21 nov. 2012
 *
 * The step represent a set of process that makes the {@link #Milestone)} goes
 * from initial state to the result state.
 */
public  interface Step<M extends Milestone<O>, O extends Object> extends Serializable, Callable<M> {

    /**
     * 
     * @param milestone
     * @return
     */
    public void setMilestone(M milestone);
    
    /**
     * 
     * @return the step identification
     */
    public Integer getStepId();

    /**
     * 
     * @return the previous step identification
     */
    public Integer getGetPreviousStepId();
    
    /**
     * @return is the step is considered as final?
     */
    public Boolean isFinalStep();
    
    /**
     * Has the step been already executed?
     * Allow to avoid to execute twice the same step
     * @return 
     */
    public Boolean hasBeenExecuted();
    
    /**
     * 
     * @return
     */
    public M getMilestone();
    
    
    /**
     * 
     * @return
     */
    public void  init(int stepId, int previousStepId, boolean finalStep);
}
