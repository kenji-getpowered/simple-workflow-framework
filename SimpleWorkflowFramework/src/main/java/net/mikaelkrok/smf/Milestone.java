/**
 * 
 */
package net.mikaelkrok.smf;

import java.io.Serializable;

/**
 * @author Mikael KROK 
 * 
 * 21 nov. 2012
 * @param <T>
 *
 */
public interface Milestone<T> extends Cloneable, Serializable  {
    
    /**
     * 
     * @return
     */
	public T getValue();
    
	/**
	 * 
	 * @param parameter
	 */
    public void setValue(T parameter);
    
    public Object clone() throws CloneNotSupportedException;
    
}
