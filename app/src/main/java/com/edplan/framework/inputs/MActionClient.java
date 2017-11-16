package com.edplan.framework.inputs;

public class MActionClient<A extends MAction>
{
	private MActionHandler<A> handler;
	
	public MActionClient(MActionHandler<A> handler){
		this.handler=handler;
	}
	
	public boolean postAction(A action){
		return handler.handle(action);
	}
}
