package com.edplan.framework.ui.additions;
import com.edplan.framework.ui.EdContainer;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.layout.EdMeasureSpec;

public class RootContainer extends EdContainer implements FrameListener
{
	/**
	 *内容层
	 */
	private EdView content;
	
	/**
	 *弹窗层
	 */
	private PopupViewLayer popupViewLayer;
	
	public RootContainer(MContext c){
		super(c);
		{
			EdLayoutParam p=new EdLayoutParam();
			p.width=Param.MODE_MATCH_PARENT;
			p.height=Param.MODE_MATCH_PARENT;
			setLayoutParam(p);
		}
		popupViewLayer=new PopupViewLayer(c);
		popupViewLayer.setParent(this);
		EdLayoutParam p=new EdLayoutParam();
		p.width=Param.MODE_MATCH_PARENT;
		p.height=Param.MODE_MATCH_PARENT;
		popupViewLayer.setLayoutParam(p);
	}

	public void setPopupViewLayer(PopupViewLayer popupViewLayer){
		this.popupViewLayer=popupViewLayer;
	}

	public PopupViewLayer getPopupViewLayer(){
		return popupViewLayer;
	}

	public void setContent(EdView content){
		this.content=content;
		content.setParent(this);
	}

	public EdView getContent(){
		return content;
	}

	@Override
	public int getChildrenCount(){
		// TODO: Implement this method
		return 2;
	}

	@Override
	public EdView getChildAt(int i){
		// TODO: Implement this method
		switch(i){
			case 0:
				return content;
			case 1:
				return popupViewLayer;
		}
		return null;
	}

	@Override
	public void onFrameStart(){
		// TODO: Implement this method
		popupViewLayer.onFrameStart();
	}
	
	@Override
	protected void onLayout(boolean changed,float left,float top,float right,float bottom){
		// TODO: Implement this method
		layoutChildren(left,top,right,bottom);
	}

	@Override
	protected void onMeasure(long widthSpec,long heightSpec){
		// TODO: Implement this method
		measureChildren(widthSpec,heightSpec);
		setMeasuredDimensition(EdMeasureSpec.getSize(widthSpec),EdMeasureSpec.getSize(heightSpec));
	}
}
