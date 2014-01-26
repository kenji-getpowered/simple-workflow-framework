/**
 * 
 */
package net.mikaelkrok.swf.workflow;

import net.mikaelkrok.swf.exception.StepAlreadyExistingException;
import net.mikaelkrok.swf.exception.StepBadPreviousException;
import net.mikaelkrok.swf.exception.StepPreviousNotExisitingException;

/**
 * @author Mikael KROK 
 * 
 * 21 nov. 2012
 * 
 * A workflow manage a sequel of {@link Step} execution to result
 * to a final {@link Milestone} 
 *
 */
public interface Workflow<O extends Object, M extends Milestone<O>, S extends Step<?, ?>> {
    
    /**
     * add a step to the chain
     * 
     * @param step
     * @throws StepAlreadyExistingException 
     * @throws StepPreviousNotExisitingException 
     * @throws StepBadPreviousException 
     */
    public void addStep(S step) throws StepAlreadyExistingException, StepPreviousNotExisitingException, StepBadPreviousException;

    /**
     * Run all step and return the final result
     * No order is defined by the interface.
     */
    public M run(M milestone);
}
