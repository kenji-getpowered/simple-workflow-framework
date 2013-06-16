/**
 * 
 */
package net.mikaelkrok.smf;

import net.mikaelkrok.smf.exception.StepAlreadyExistingException;
import net.mikaelkrok.smf.exception.StepBadPreviousException;
import net.mikaelkrok.smf.exception.StepPreviousNotExisitingException;

/**
 * @author Mikael KROK 
 * 
 * 21 nov. 2012
 * 
 * A workflow manage a sequel of {@link Step} execution to result
 * to a final {@link Milestone} 
 *
 */
public interface Workflow<U extends Object, V extends Milestone<U>, T extends Step<?, ?, ?, ?>> {
    
    /**
     * add a step to the chain
     * 
     * @param step
     * @throws StepAlreadyExistingException 
     * @throws StepPreviousNotExisitingException 
     * @throws StepBadPreviousException 
     */
    public void addStep(T step) throws StepAlreadyExistingException, StepPreviousNotExisitingException, StepBadPreviousException;

    /**
     * Run all step and return the final result
     * No order is defined by the interface.
     */
    public V run(V milestone);
}
