package com.edplan.nso.Ruleset.std.objects.drawables;
import com.edplan.framework.graphics.line.LinePath;
import com.edplan.framework.math.Vec2;
import com.edplan.nso.Ruleset.std.objects.StdSlider;
import java.util.ArrayList;
import java.util.List;
import com.edplan.nso.Ruleset.std.objects.StdPath;
import com.edplan.framework.graphics.line.approximator.CircleApproximator;
import com.edplan.framework.graphics.line.approximator.BezierApproximator;

public class DrawableStdSlider
{
	private StdPath slider;
	
	private LinePath path;
	
	public DrawableStdSlider(StdPath sld){
		slider=sld;
		path=new LinePath();
	}
	
	public List<Vec2> getControlPoint(){
		return slider.getControlPoints();
	}
	
	public List<Vec2> calculateSubPath(List<Vec2> subPoints){
		switch(slider.getType()){
			case Linear:
				return subPoints;
			case Perfect:
				if(getControlPoint().size()!=3||subPoints.size()!=3){
					break;
				}else{
					List<Vec2> sub=(new CircleApproximator(subPoints.get(0),subPoints.get(1),subPoints.get(2))).createArc();
					if(sub.size()!=0)return sub;
				}
				break;
		}
		return (new BezierApproximator(subPoints)).createBezier();
	}
	
	public LinePath calculatePath(){
		path.clearPositionData();
		List<Vec2> subControlPoints=new ArrayList<Vec2>();
		for(int i=0;i<getControlPoint().size();i++){
			subControlPoints.add(getControlPoint().get(i));
			if(i==getControlPoint().size()-1||getControlPoint().get(i).equals(getControlPoint().get(i+1))){
				List<Vec2> subPath=calculateSubPath(subControlPoints);
				for(Vec2 v:subPath){
					if(path.size()==0||!path.getLast().equals(v)){
						path.add(v);
					}
				}
				subControlPoints.clear();
			}
		}
		return path;
	}
}
