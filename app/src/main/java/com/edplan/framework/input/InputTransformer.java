package com.edplan.framework.input;

public interface InputTransformer<F extends InputEvent,S extends InputEvent>
{
	public S transform(F raw);
}
