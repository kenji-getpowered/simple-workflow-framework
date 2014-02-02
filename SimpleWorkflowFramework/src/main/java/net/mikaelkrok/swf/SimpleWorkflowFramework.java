/**
 * 
 */
package net.mikaelkrok.swf;

import java.util.List;

import net.mikaelkrok.swf.exception.StepAlreadyExistingException;
import net.mikaelkrok.swf.exception.StepBadPreviousException;
import net.mikaelkrok.swf.exception.StepPreviousNotExisitingException;
import net.mikaelkrok.swf.executor.ExecutorCallableWorkflow;
import net.mikaelkrok.swf.workflow.Milestone;
import net.mikaelkrok.swf.workflow.Step;
import net.mikaelkrok.swf.workflow.milestone.string.StringMilestoneImpl;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Mikael KROK 
 * 
 *         18 déc. 2012
 * 
 */
@Service
public class SimpleWorkflowFramework {

	private static org.apache.log4j.Logger LOGGER = Logger.getLogger(SimpleWorkflowFramework.class);

	public void run(){
	
		/* ************************************************************
		 * 
		 *  Service initialization
		 * 
		 * ************************************************************		
		 */
		

		// Spring context
		ApplicationContext context = 
              new ClassPathXmlApplicationContext("application-context.xml");
		
		// Execution tool
		ExecutorCallableWorkflow<String, Milestone<String>, Step<Milestone<String>, String>> callableWorkflow = 
				new ExecutorCallableWorkflow<String, Milestone<String>, Step<Milestone<String>, String>>();
		
		// Starting point
		Milestone<String> milestone = new StringMilestoneImpl();
		milestone.setValue(" begin");
		
		
		/*
		 * Step 2 requires Step 1 Step 5 requires Step 1
		 * 
		 * Step 6 requires Step 2 Step 3 requires Step 2
		 * 
		 * 1 : 2, 5 2 : 6, 3 6 : 7
		 */
		try {
			
			Step<Milestone<String>, String> stringAdderStep = (Step<Milestone<String>, String>) context.getBean("stringAdderStep");
			stringAdderStep.init(1, 0, true);
 			callableWorkflow.addStep(stringAdderStep);
			
			Step<Milestone<String>, String>  bean = (Step<Milestone<String>, String>) context.getBean("stringStepCapitalizer");
			bean.init(2, 1, false);
			callableWorkflow.addStep(bean);
//			
//			callableStep2.init(2, 1, false);
//			callableStep3.init(3, 2, true);
//			callableStep5.init(5, 1, true);
//			callableStep6.init(6, 2, false);
//			callableStep7.init(7, 6, true);
//			callableStep8.init(8, 7, true);
//			callableStep9.init(9, 8, true);
//			callableWorkflow.addStep(callableStep2);
//			callableWorkflow.addStep(callableStep3);
//			callableWorkflow.addStep(callableStep5);
//			callableWorkflow.addStep(callableStep6);
//			callableWorkflow.addStep(callableStep7);
//			callableWorkflow.addStep(callableStep8);
//			callableWorkflow.addStep(callableStep9);

			// for (int i = 0; i < 5; i++) {
			callableWorkflow.run(milestone);
			List<Step<Milestone<String>, String>> finalSteps = callableWorkflow.getFinalSteps();
			System.out
					.println("*************** Final Steps *********************");
			for (Step<?, ?> finalStep : finalSteps) {
				LOGGER.info("StepCallableWorkflow.main() Step "
						+ finalStep.getStepId() + "  result :"
						+ finalStep.getMilestone().getValue().toString());
			}
			// }
		} catch (StepAlreadyExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepPreviousNotExisitingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StepBadPreviousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
