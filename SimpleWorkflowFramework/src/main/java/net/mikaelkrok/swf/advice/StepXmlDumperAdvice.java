package net.mikaelkrok.swf.advice;

import net.mikaelkrok.swf.workflow.Step;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Mikael KROK
 * 
 * This adivce allow the step to be dumpped before and after its execution
 *
 */
@Aspect
public class StepXmlDumperAdvice  {
	
	private final static XStream xstream = new XStream();

    @Around(value="execution(* net.mikaelkrok.swf.workflow.Step+.call())")
    public void aroundCall(ProceedingJoinPoint pjp){
    	
    	Object target = pjp.getTarget();
    	if (target instanceof Step<?, ?>) {
    		Step<?, ?> targetStep = (Step<?, ?>) target;
    		System.out.println("XML_BEFORE= "+ xstream.toXML(targetStep.getMilestone()));
    		try {
    			pjp.proceed();
    			System.out.println("XML_AFTER= "+ xstream.toXML(targetStep.getMilestone()));
    		} catch (Throwable e) {
    			e.printStackTrace();
    		}
    	}
    }
}
