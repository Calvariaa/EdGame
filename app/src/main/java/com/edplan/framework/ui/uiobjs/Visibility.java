package com.edplan.framework.ui.uiobjs;

public enum Visibility{
	Visible(true,true),Hidden(false,true),Gone(false,false);
	public final boolean needDraw;
	public final boolean needMesure;
	Visibility(boolean d,boolean m){
		this.needDraw=d;
		this.needMesure=m;
	}
}
