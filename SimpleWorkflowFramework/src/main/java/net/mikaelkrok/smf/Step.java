/**
 * 
 */
package net.mikaelkrok.smf;


/**
 * @author Mikael KROK 
 * 
 * 21 nov. 2012
 *
 * The step represent a set of process that makes the {@link #Milestone)} goes
 * from initial state to the result state
 */
public  interface Step<T extends Milestone<U>, U extends Object> {

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
     * @return is the step is considered as final?
     */
    public Boolean hasBeenExecuted();
    
    /**
     * 
     * @return
     */
    public T getMilestone();
}
