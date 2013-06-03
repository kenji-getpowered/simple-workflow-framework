/**
 * 
 */
package net.mikaelkrok.smf.exception;

import net.mikaelkrok.smf.Workflow;

/**
 * @author Mikael KROK 
 * 
 * 28 déc. 2012
 * 
 * When adding {@link Step} to a {@link Workflow} you can give a
 * previous id of {@link Step} that need to be executed before this one
 * If the id of the {@link Step} is not already i the workflow, this
 * exception must be raised
 *
 */
public class StepPreviousNotExisitingException extends Exception {

}
