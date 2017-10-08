package com.edplan.simpleGame.audio.bass;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.FIELD)
public abstract @interface BassType
{
	public BassChannel.Type type();
}
