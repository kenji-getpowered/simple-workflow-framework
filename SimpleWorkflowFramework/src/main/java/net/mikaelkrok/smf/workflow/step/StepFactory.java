package net.mikaelkrok.smf.workflow.step;

import net.mikaelkrok.smf.workflow.Step;

import org.springframework.beans.factory.FactoryBean;

public class StepFactory implements FactoryBean<Step<?,?>> {

	@Override
	public Step<?,?> getObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Step<?,?>> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}


}
