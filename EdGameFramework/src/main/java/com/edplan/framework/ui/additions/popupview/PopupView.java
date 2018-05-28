package com.edplan.framework.ui.additions.popupview;
import com.edplan.framework.ui.widget.RelativeContainer;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.widget.component.Hideable;

public class PopupView extends RelativeContainer implements Hideable
{
	private boolean hideWhenBackpress=true;
	
	public PopupView(MContext c){
		super(c);
	}

	public void setHideWhenBackpress(boolean hideWhenBackpress){
		this.hideWhenBackpress=hideWhenBackpress;
	}

	public boolean isHideWhenBackpress(){
		return hideWhenBackpress;
	}

	@Override
	public boolean onBackPressed(){
		// TODO: Implement this method
		if(isHideWhenBackpress()){
			hide();
		}
		return true;
	}
	
	protected void onHide(){
		setVisiblility(VISIBILITY_GONE);
	}
	
	protected void onShow(){
		
	}

	@Override
	public final void hide(){
		// TODO: Implement this method
		onHide();
	}

	@Override
	public final void show(){
		// TODO: Implement this method
		getContext().getViewRoot().getPopupViewLayer().register(this);
		onShow();
	}

	@Override
	public boolean isHidden(){
		// TODO: Implement this method
		return getVisiblility()==VISIBILITY_GONE;
	}
}
