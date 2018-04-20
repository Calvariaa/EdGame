package com.edplan.framework.ui.animation;
import com.edplan.framework.interfaces.Reflection;

public enum LoopType
{
	None(new Reflection<Integer,Boolean>(){
			@Override
			public Boolean invoke(Integer t)
			{
				// TODO: Implement this method
				return false;
			}
		}),
	Loop(new Reflection<Integer,Boolean>(){
			@Override
			public Boolean invoke(Integer t)
			{
				// TODO: Implement this method
				return false;
			}
		}),
	LoopAndReverse(new Reflection<Integer,Boolean>(){
			@Override
			public Boolean invoke(Integer t)
			{
				// TODO: Implement this method
				return t%2==1;
			}
		}),
	Endless(new Reflection<Integer,Boolean>(){
		@Override
		public Boolean invoke(Integer t)
		{
			// TODO: Implement this method
			return false;
		}
	});
	
	private final Reflection<Integer,Boolean> reverseRef;
	
	LoopType(Reflection<Integer,Boolean> reverseRef){
		this.reverseRef=reverseRef;
	}
	
	public boolean isReverse(int loopCount){
		return reverseRef.invoke(loopCount);
	}
}
