package com.edplan.simpleGame.inputs;

public interface MActionHandler<A extends MAction>
{
	public boolean handle(A action);
}
