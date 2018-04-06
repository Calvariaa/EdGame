package com.edplan.framework.ui.looper;
import com.edplan.superutils.interfaces.AbstractLooper;
import com.edplan.superutils.interfaces.Loopable;
import com.edplan.superutils.interfaces.Loopable.Flag;

public class StepLooper extends BaseLooper<StepedLoopable>
{
	private int step;

	protected void setStep(int step) {
		this.step=step;
	}

	public int getStep() {
		return step;
	}

	public void addLoopable(Loopable l,int step){
		addLoopable(new WrapStepedLoopable(l,step));
	}
	
	@Override
	protected void runLoopable(StepedLoopable l) {
		// TODO: Implement this method
		setStep(l.getStep());
		super.runLoopable(l);
	}
	
	public class WrapStepedLoopable extends StepedLoopable {
		
		private int step=-1;
		
		private Loopable loopable;
		
		public WrapStepedLoopable(Loopable l,int step){
			this.loopable=l;
			this.step=step;
		}
		
		@Override
		public void onLoop(double deltaTime) {
			// TODO: Implement this method
			loopable.onLoop(deltaTime);
		}

		@Override
		public void onRemove() {
			// TODO: Implement this method
			loopable.onRemove();
		}

		@Override
		public Loopable.Flag getFlag() {
			// TODO: Implement this method
			return loopable.getFlag();
		}

		
		@Override
		public void setFlag(Loopable.Flag flag) {
			// TODO: Implement this method
			loopable.setFlag(flag);
		}

		@Override
		public AbstractLooper getLooper() {
			// TODO: Implement this method
			return loopable.getLooper();
		}

		@Override
		public void setLooper(AbstractLooper lp) {
			// TODO: Implement this method
			loopable.setLooper(lp);
		}

		@Override
		public int getStep() {
			// TODO: Implement this method
			return step;
		}
	}
}
