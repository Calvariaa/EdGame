package com.edplan.nso.storyboard.elements;

import com.edplan.framework.graphics.opengl.BlendType;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.Vec2;
import java.util.List;

public class Selecters {
	public static final CommandTimelineSelecter<Float> SX=new CommandTimelineSelecter<Float>(){
		@Override
		public List<TypedCommand<Float>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.X.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Float> SY=new CommandTimelineSelecter<Float>(){
		@Override
		public List<TypedCommand<Float>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.Y.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Vec2> SScale=new CommandTimelineSelecter<Vec2>(){
		@Override
		public List<TypedCommand<Vec2>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.Scale.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Float> SRotation=new CommandTimelineSelecter<Float>(){
		@Override
		public List<TypedCommand<Float>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.Rotation.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Color4> SColour=new CommandTimelineSelecter<Color4>(){
		@Override
		public List<TypedCommand<Color4>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.Colour.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Float> SAlpha=new CommandTimelineSelecter<Float>(){
		@Override
		public List<TypedCommand<Float>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.Alpha.getCommands();
		}
	};
	public static final CommandTimelineSelecter<BlendType> SBlendType=new CommandTimelineSelecter<BlendType>(){
		@Override
		public List<TypedCommand<BlendType>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.BlendingMode.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Boolean> SFlipH=new CommandTimelineSelecter<Boolean>(){
		@Override
		public List<TypedCommand<Boolean>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.FlipH.getCommands();
		}
	};
	public static final CommandTimelineSelecter<Boolean> SFlipV=new CommandTimelineSelecter<Boolean>(){
		@Override
		public List<TypedCommand<Boolean>> select(CommandTimeLineGroup group) {
			// TODO: Implement this method
			return group.FlipV.getCommands();
		}
	};
}
