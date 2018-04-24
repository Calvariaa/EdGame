package com.edplan.framework.test.performance;
import com.edplan.framework.Framework;
import java.util.ArrayList;
import java.util.HashMap;

public class Tracker
{
	private static boolean enable=true;
	
	private static ArrayList<TrackNode> nodes;
	private static HashMap<String,TrackNode> namemap;
	
	
	public static class TrackNode{
		public double totalTimeMS;
		public long trackedTimes;
		public double latestRecordTime;
		public int id;
		public String name;
		
		private int stack=0;
		
		public TrackNode(int id,String name){
			this.id=id;
			this.name=name;
		}
		
		public void watch(){
			if(stack==0){
				latestRecordTime=Framework.relativePreciseTimeMillion();
			}else{
				double time=Framework.relativePreciseTimeMillion();
				totalTimeMS+=time-latestRecordTime;
				latestRecordTime=time;
			}
			stack++;
		}
		
		public void end(){
			trackedTimes++;
			stack--;
			if(stack==0){
				totalTimeMS+=Framework.relativePreciseTimeMillion()-latestRecordTime;
			}else{
				double time=Framework.relativePreciseTimeMillion();
				totalTimeMS+=time-latestRecordTime;
				latestRecordTime=time;
			}
		}

		@Override
		public String toString() {
			// TODO: Implement this method
			StringBuilder sb=new StringBuilder();
			sb.append("------------------------------------\n");
			sb.append("name         : "+name+" ("+id+")\n");
			sb.append("totalTime    : "+totalTimeMS+"ms\n");
			sb.append("trackedTimes : "+trackedTimes+"\n");
			sb.append("------------------------------------\n");
			return sb.toString();
		}
	}
}
