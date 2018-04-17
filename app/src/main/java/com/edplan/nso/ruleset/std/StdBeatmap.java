package com.edplan.nso.ruleset.std;
import com.edplan.nso.OsuBeatmap;
import com.edplan.nso.filepart.PartColours;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.nso.filepart.PartEditor;
import com.edplan.nso.filepart.PartEvents;
import com.edplan.nso.filepart.PartGeneral;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.filepart.PartMetadata;
import com.edplan.nso.filepart.PartTimingPoints;
import com.edplan.nso.beatmap.StdBeatmapBindingData;
import com.edplan.nso.ruleset.std.objects.StdHitObjects;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.std.objects.StdSpinner;
import com.edplan.nso.ruleset.std.objects.StdHitCircle;
import com.edplan.nso.ruleset.std.objects.StdSlider;

public class StdBeatmap extends OsuBeatmap
{
	public static int SUPPORT_VERSION=14;

	private int version;
	private PartGeneral general;
	private PartEditor editor;
	private PartMetadata metadata;
	private PartDifficulty difficulty;
	private PartEvents event;
	private PartTimingPoints timingPoints;
	private PartColours colours;
	private StdHitObjects<StdHitObject> hitObjects;

	public StdBeatmap(){
		
	}
	
	public StdBeatmap(StdBeatmapBindingData bd){
		setVersion(bd.getVersion());
		setGeneral(bd.getGeneral());
		setEditor(bd.getEditor());
		setMetadata(bd.getMetadata());
		setDifficulty(bd.getDifficulty());
		setEvent(bd.getEvent());
		setColours(bd.getColours());
	}

	public void setHitObjects(StdHitObjects<StdHitObject> hitObjects){
		this.hitObjects=hitObjects;
	}

	public StdHitObjects<StdHitObject> getHitObjects(){
		return hitObjects;
	}
	
	public void setVersion(int version){
		this.version=version;
	}

	public int getVersion(){
		return version;
	}

	public void setGeneral(PartGeneral general){
		this.general=general;
	}

	public PartGeneral getGeneral(){
		return general;
	}

	public void setEditor(PartEditor editor){
		this.editor=editor;
	}

	public PartEditor getEditor(){
		return editor;
	}

	public void setMetadata(PartMetadata metadata){
		this.metadata=metadata;
	}

	public PartMetadata getMetadata(){
		return metadata;
	}

	public void setDifficulty(PartDifficulty difficulty){
		this.difficulty=difficulty;
	}

	public PartDifficulty getDifficulty(){
		return difficulty;
	}

	public void setEvent(PartEvents event){
		this.event=event;
	}

	public PartEvents getEvent(){
		return event;
	}

	public void setTimingPoints(PartTimingPoints timingPoints){
		this.timingPoints=timingPoints;
	}

	public PartTimingPoints getTimingPoints(){
		return timingPoints;
	}

	public void setColours(PartColours colours){
		this.colours=colours;
	}

	public PartColours getColours(){
		return colours;
	}
	
	static final int stack_distance = 3;
	private void applyStacking()
	{
		/*

		// Reset stacking
		for (int i = 0; i <= hitObjects.size() - 1; i++){
			hitObjects[i].StackHeight = 0;
		}

		// Extend the end index to include objects they are stacked on
		int extendedEndIndex = hitObjects.size() - 1;
		for (int i = hitObjects.size() - 1; i >= 0; i--)
		{
			int stackBaseIndex = i;
			for (int n = stackBaseIndex + 1; n < hitObjects.size(); n++)
			{
				StdHitObject stackBaseObject =hitObjects.get(stackBaseIndex);
				if (stackBaseObject instanceof StdSpinner) break;

				StdHitObject objectN = hitObjects.get(n);
				if (objectN instanceof StdSpinner)
				continue;

				double endTime = stackBaseObject.getStartTime(); //(stackBaseObject as IHasEndTime)?.EndTime ?? stackBaseObject.StartTime;
				double stackThreshold = objectN.TimePreempt * beatmap.BeatmapInfo?.StackLeniency ?? 0.7f;

				if (objectN.StartTime - endTime > stackThreshold)
				//We are no longer within stacking range of the next object.
					break;

				if (Vector2Extensions.Distance(stackBaseObject.Position, objectN.Position) < stack_distance ||
					stackBaseObject is Slider && Vector2Extensions.Distance(stackBaseObject.EndPosition, objectN.Position) < stack_distance)
				{
					stackBaseIndex = n;

					// HitObjects after the specified update range haven't been reset yet
					objectN.StackHeight = 0;
				}
			}

			if (stackBaseIndex > extendedEndIndex)
			{
				extendedEndIndex = stackBaseIndex;
				if (extendedEndIndex == hitObjects.size() - 1)
					break;
			}
		}

		//Reverse pass for stack calculation.
		int extendedStartIndex = 0;
		for (int i = extendedEndIndex; i > 0; i--)
		{
			int n = i;
			/* We should check every note which has not yet got a stack.
			 * Consider the case we have two interwound stacks and this will make sense.
			 *
			 * o <-1      o <-2
			 *  o <-3      o <-4
			 *
			 * We first process starting from 4 and handle 2,
			 * then we come backwards on the i loop iteration until we reach 3 and handle 1.
			 * 2 and 1 will be ignored in the i loop because they already have a stack value.
			 */

			 /*
			StdHitObject objectI = hitObjects.get(i);
			if (objectI.StackHeight != 0 || objectI instanceof StdSpinner) continue;

			double stackThreshold = objectI.TimePreempt * getGeneral().getStackLeniency();

			/* If this object is a hitcircle, then we enter this "special" case.
			 * It either ends with a stack of hitcircles only, or a stack of hitcircles that are underneath a slider.
			 * Any other case is handled by the "is Slider" code below this.
			 */
			 /*
			if (objectI instanceof StdHitCircle)
			{
				while (--n >= 0)
				{
					StdHitObject objectN = hitObjects.get(n);
					if (objectN instanceof StdSpinner) continue;

					double endTime = (objectN as IHasEndTime)?.EndTime ?? objectN.StartTime;

					if (objectI.StartTime - endTime > stackThreshold)
					//We are no longer within stacking range of the previous object.
						break;

					// HitObjects before the specified update range haven't been reset yet
					if (n < extendedStartIndex)
					{
						objectN.StackHeight = 0;
						extendedStartIndex = n;
					}

					/* This is a special case where hticircles are moved DOWN and RIGHT (negative stacking) if they are under the *last* slider in a stacked pattern.
					 *    o==o <- slider is at original location
					 *        o <- hitCircle has stack of -1
					 *         o <- hitCircle has stack of -2
					 */
					 /*
					if (objectN instanceof StdSlider && Vector2Extensions.Distance(objectN.EndPosition, objectI.Position) < stack_distance)
					{
						int offset = objectI.StackHeight - objectN.StackHeight + 1;
						for (int j = n + 1; j <= i; j++)
						{
							//For each object which was declared under this slider, we will offset it to appear *below* the slider end (rather than above).
							StdHitObject objectJ = hitObjects.get(j) ;
							if (Vector2Extensions.Distance(objectN.EndPosition, objectJ.Position) < stack_distance)
								objectJ.StackHeight -= offset;
						}

						//We have hit a slider.  We should restart calculation using this as the new base.
						//Breaking here will mean that the slider still has StackCount of 0, so will be handled in the i-outer-loop.
						break;
					}

					if (Vector2Extensions.Distance(objectN.Position, objectI.Position) < stack_distance)
					{
						//Keep processing as if there are no sliders.  If we come across a slider, this gets cancelled out.
						//NOTE: Sliders with start positions stacking are a special case that is also handled here.

						objectN.StackHeight = objectI.StackHeight + 1;
						objectI = objectN;
					}
				}
			}
			else if (objectI instanceof StdSlider)
			{
				/* We have hit the first slider in a possible stack.
				 * From this point on, we ALWAYS stack positive regardless.
				 */
				 /*
				while (--n >= 0)
				{
					StdHitObject objectN = hitObjects.get(n);
					if (objectN instanceof StdSpinner) continue;

					if (objectI.StartTime - objectN.StartTime > stackThreshold)
					//We are no longer within stacking range of the previous object.
						break;

					if (Vector2Extensions.Distance(objectN.EndPosition, objectI.Position) < stack_distance)
					{
						objectN.StackHeight = objectI.StackHeight + 1;
						objectI = objectN;
					}
				}
			}
		}
	}*/
    }
}
