package com.edplan.nso.Ruleset.amodel.parser;
import com.edplan.nso.Ruleset.amodel.object.HitObject;
import com.edplan.nso.NsoException;

public interface HitObjectReparser<T extends HitObject>
{
	public String reparse(T t) throws NsoException;
}
