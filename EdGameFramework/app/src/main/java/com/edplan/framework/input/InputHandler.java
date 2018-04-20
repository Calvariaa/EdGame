package com.edplan.framework.input;

public interface InputHandler<E extends InputEvent>
{
	public boolean handleEvent(E event);
}
