/**
 * 
 */
package net.mikaelkrok.smf;

import java.io.Serializable;


/**
 * @author Mikael KROK 
 * 
 * 21 nov. 2012
 *
 * The step represent a set of process that makes the {@link #Milestone)} goes
 * from initial state to the result state.
 */
public  interface Step<T extends Milestone<U>, U extends Object> extends Serializable {

    /**
     * 
     * @param milestone
     * @return
     */
    public void setMilestone(T milestone);
    
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
    public T getMilestone();
}
