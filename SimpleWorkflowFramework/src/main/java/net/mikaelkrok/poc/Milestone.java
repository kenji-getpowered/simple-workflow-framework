/**
 * 
 */
package net.mikaelkrok.poc;

/**
 * @author Mikael KROK
 * 
 * 21 nov. 2012
 * @param <T>
 *
 */
public interface Milestone<T> extends Cloneable {
    
    public T getValue();
    
    public void setValue(T parameter);
    
    public Object clone() throws CloneNotSupportedException;
    
}
