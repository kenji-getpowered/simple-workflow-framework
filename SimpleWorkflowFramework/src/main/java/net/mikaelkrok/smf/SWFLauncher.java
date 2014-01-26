package net.mikaelkrok.smf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SWFLauncher {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ApplicationContext context = 
	              new ClassPathXmlApplicationContext("application-context.xml");
		
		SimpleWorkflowFramework callableWorkflow = (SimpleWorkflowFramework) context.getBean("simpleWorkflowFramework");
		callableWorkflow.run();
	}

}
