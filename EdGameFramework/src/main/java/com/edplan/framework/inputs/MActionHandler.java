package com.edplan.framework.inputs;

public interface MActionHandler<A extends MAction>
{
	public boolean handle(A action);
}
