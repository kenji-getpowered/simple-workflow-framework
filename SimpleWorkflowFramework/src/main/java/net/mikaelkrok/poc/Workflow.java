/**
 * 
 */
package net.mikaelkrok.poc;


/**
 * @author Mikael KROK
 * 
 * 21 nov. 2012
 *
 */
public interface Workflow<U extends Object, V extends Milestone<U>> {
    
    /**
     * Run all step and return the final result
     * No order is defined by the interface.
     */
    public V run(V milestone);
}
