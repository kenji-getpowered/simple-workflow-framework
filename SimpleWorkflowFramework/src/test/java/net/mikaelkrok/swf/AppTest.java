package net.mikaelkrok.swf;

import java.util.List;

import net.mikaelkrok.swf.exception.StepAlreadyExistingException;
import net.mikaelkrok.swf.exception.StepBadPreviousException;
import net.mikaelkrok.swf.exception.StepPreviousNotExisitingException;
import net.mikaelkrok.swf.executor.ExecutorCallableWorkflow;
import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.milestone.string.StringMilestoneImpl;
import net.mikaelkrok.swf.workflow.step.StepImpl;
import net.mikaelkrok.swf.workflow.step.string.StringAdderStep;
import net.mikaelkrok.swf.workflow.step.string.StringStepCapitalizer;
import net.mikaelkrok.swf.workflow.step.string.StringStepTrimmer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author 884002
 *
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//    	try{
//    		
//		} catch (StepAlreadyExistingException e) {
//			fail(e.getMessage());
//		} catch (StepPreviousNotExisitingException e) {
//			fail(e.getMessage());
//		} catch (StepBadPreviousException e) {
//			fail(e.getMessage());
//		}
    }
}
