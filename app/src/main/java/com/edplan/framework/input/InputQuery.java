package com.edplan.framework.input;
import com.edplan.superutils.classes.SafeList;
import java.util.Iterator;

public class InputQuery<E extends InputEvent>
{
	private InputHandler<E> inputHandler;
	
	private SafeList<E> eventList=new SafeList<E>();

	public void setInputHandler(InputHandler<E> inputHandler) {
		this.inputHandler=inputHandler;
	}

	public InputHandler<E> getInputHandler() {
		return inputHandler;
	}
	
	/**
	 *推送一个事件到队列中
	 */
	public void sendEvent(E event){
		eventList.add(event);
	}
	
	/**
	 *处理队列中的事件并移处事件
	 */
	public void handleEvents(){
		if(inputHandler!=null){
			eventList.startIterate();
			Iterator<E> iter=eventList.iterator();
			while(iter.hasNext()){
				inputHandler.handleEvent(iter.next());
				iter.remove();
			}
			eventList.endIterate();
		}
	}
}
