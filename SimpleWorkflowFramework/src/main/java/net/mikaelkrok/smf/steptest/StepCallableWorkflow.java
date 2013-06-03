/**
 * 
 */
package net.mikaelkrok.smf.steptest;

import java.util.List;

import net.mikaelkrok.smf.Milestone;
import net.mikaelkrok.smf.exception.StepAlreadyExistingException;
import net.mikaelkrok.smf.exception.StepBadPreviousException;
import net.mikaelkrok.smf.exception.StepPreviousNotExisitingException;
import net.mikaelkrok.smf.executor.CallableStep;
import net.mikaelkrok.smf.executor.ExecutorCallableWorkflow;
import net.mikaelkrok.smf.string.StringAdderCallableStep;
import net.mikaelkrok.smf.string.StringMilestoneImpl;
import net.mikaelkrok.smf.string.StringStepCapitalizer;
import net.mikaelkrok.smf.string.StringStepTrimmer;

/**
 * @author Mikael KROK 
 * 
 *         18 déc. 2012
 * 
 */
public class StepCallableWorkflow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorCallableWorkflow<String, Milestone<String>> callableWorkflow = new ExecutorCallableWorkflow<String, Milestone<String>>();
		Milestone<String> milestone = new StringMilestoneImpl();
		milestone.setValue(" begin");
		/*
		 * Step 2 requires Step 1 Step 5 requires Step 1
		 * 
		 * Step 6 requires Step 2 Step 3 requires Step 2
		 * 
		 * 1 : 2, 5 2 : 6, 3 6 : 7
		 */
		StringAdderCallableStep<Milestone<String>> callableStep = new StringAdderCallableStep<Milestone<String>>(
				1, false);
		try {
			callableWorkflow.addStep(callableStep);

			StringAdderCallableStep<Milestone<String>> callableStep2 = new StringAdderCallableStep<Milestone<String>>(
					2, 1, false);
			callableWorkflow.addStep(callableStep2);

			StringAdderCallableStep<Milestone<String>> callableStep3 = new StringAdderCallableStep<Milestone<String>>(
					3, 2, true);
			callableWorkflow.addStep(callableStep3);

			StringAdderCallableStep<Milestone<String>> callableStep5 = new StringAdderCallableStep<Milestone<String>>(
					5, 1, true);
			callableWorkflow.addStep(callableStep5);

			StringAdderCallableStep<Milestone<String>> callableStep6 = new StringAdderCallableStep<Milestone<String>>(
					6, 2, false);
			callableWorkflow.addStep(callableStep6);

			StringAdderCallableStep<Milestone<String>> callableStep7 = new StringAdderCallableStep<Milestone<String>>(
					7, 6, true);
			callableWorkflow.addStep(callableStep7);

			StringStepCapitalizer<Milestone<String>> callableStep8 = new StringStepCapitalizer<Milestone<String>>(
					8, 7, true);
			callableWorkflow.addStep(callableStep8);

			StringStepTrimmer<Milestone<String>> callableStep9 = new StringStepTrimmer<Milestone<String>>(
					9, 8, true);
			callableWorkflow.addStep(callableStep9);

			// for (int i = 0; i < 5; i++) {
			callableWorkflow.run(milestone);
			List<CallableStep> finalSteps = callableWorkflow.getFinalSteps();
			System.out
					.println("*************** Final Steps *********************");
			for (CallableStep<?, ?> finalStep : finalSteps) {
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
