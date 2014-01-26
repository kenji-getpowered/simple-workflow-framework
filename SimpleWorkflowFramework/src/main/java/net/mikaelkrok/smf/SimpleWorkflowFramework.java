/**
 * 
 */
package net.mikaelkrok.smf;

import java.util.List;

import net.mikaelkrok.smf.exception.StepAlreadyExistingException;
import net.mikaelkrok.smf.exception.StepBadPreviousException;
import net.mikaelkrok.smf.exception.StepPreviousNotExisitingException;
import net.mikaelkrok.smf.executor.ExecutorCallableWorkflow;
import net.mikaelkrok.smf.workflow.Milestone;
import net.mikaelkrok.smf.workflow.Step;
import net.mikaelkrok.smf.workflow.milestone.string.StringMilestoneImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Mikael KROK 
 * 
 *         18 d�c. 2012
 * 
 */
@Service
public class SimpleWorkflowFramework {
	
	@Autowired  
	@Qualifier("stringAdderStep")
	private Step<Milestone<String>, String> stringAdderStep;//;// = new StringAdderStep<Milestone<String>>();
	
//	@Autowired
//	private StringAdderStep<Milestone<String>> stringAdderStep2;// = new StringAdderStep<Milestone<String>>();
//	
//	@Autowired
//	private StringAdderStep<Milestone<String>> stringAdderStep3;// = new StringAdderStep<Milestone<String>>();
//	
//	@Autowired
//	private StringAdderStep<Milestone<String>> stringAdderStep5;// = new StringAdderStep<Milestone<String>>();
//	
//	@Autowired
//	private StringAdderStep<Milestone<String>> stringAdderStep6;// = new StringAdderStep<Milestone<String>>();
//	
//	@Autowired
//	private StringAdderStep<Milestone<String>> stringAdderStep7;// = new StringAdderStep<Milestone<String>>();
//	
//	@Autowired
//	private StringStepCapitalizer<Milestone<String>> stringStepCapitalizer;// = new StringStepCapitalizer<Milestone<String>>();
//	
//	@Autowired
//	private StringStepTrimmer<Milestone<String>> stringStepTrimmer;// = new StringStepTrimmer<Milestone<String>>();
////	
	public void run(){
	      
		ExecutorCallableWorkflow<String, Milestone<String>, Step<Milestone<String>, String>> callableWorkflow = 
				new ExecutorCallableWorkflow<String, Milestone<String>, Step<Milestone<String>, String>>();
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
			stringAdderStep.init(1, 0, true);
;			stringAdderStep.setMilestone(milestone);
			callableWorkflow.addStep(stringAdderStep);
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
				System.out.println("StepCallableWorkflow.main() Step "
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
