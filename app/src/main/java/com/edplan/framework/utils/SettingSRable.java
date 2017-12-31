package com.edplan.framework.utils;
import com.edplan.framework.interfaces.Copyable;

public class SettingSRable<T extends SettingState> extends AbstractSRable<T>
{
	private T defValue;
	
	public SettingSRable(T def){
		defValue=def;
		initial();
		getData().set();
	}
	
	@Override
	public void onSave(T t) {
		// TODO: Implement this method
	}

	@Override
	public void onRestore(T now,T pre) {
		// TODO: Implement this method
		now.set();
	}

	@Override
	public T getDefData() {
		// TODO: Implement this method
		return defValue;
	}

	@Override
	public void setCurrentData(T t) {
		// TODO: Implement this method
		super.setCurrentData(t);
		t.set();
	}
}
