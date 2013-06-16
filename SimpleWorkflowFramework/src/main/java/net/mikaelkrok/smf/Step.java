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
 * Initial state can correspond to a particular Milstone (such as String) and 
 * results in a different Milstone (for instance xml).
 */
public  interface Step<T extends Milestone<A>, A extends Object , U extends Milestone<B> ,B extends Object> extends Serializable {

    /**
     * 
     * @param milestone
     * @return
     */
    public void setMilestone(T milestone);
    
    /**
     * Can return a milestone depending from a different type 
     * from the input
     * @return
     */
    public U getMilestone();
    
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
    

}
