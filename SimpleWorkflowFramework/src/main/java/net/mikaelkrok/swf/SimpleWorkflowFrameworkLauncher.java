package net.mikaelkrok.swf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleWorkflowFrameworkLauncher {
	
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
