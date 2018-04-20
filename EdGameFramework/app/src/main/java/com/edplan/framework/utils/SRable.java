package com.edplan.framework.utils;
import com.edplan.framework.interfaces.Copyable;

public class SRable<T extends Copyable> extends AbstractSRable<T>
{
	private SROperation<T> op;
	
	public SRable(SROperation<T> op){
		this.op=op;
	}
	
	@Override
	public void onSave(T t) {
		// TODO: Implement this method
		op.onSave(t);
	}

	@Override
	public void onRestore(T now,T pre) {
		// TODO: Implement this method
		op.onRestore(now,pre);
	}

	@Override
	public T getDefData() {
		// TODO: Implement this method
		return op.getDefData();
	}
	
	public interface SROperation<T extends Copyable>{
		public void onSave(T v);
		
		public void onRestore(T now,T pre);
		
		public T getDefData();
	}
}
