package com.edplan.nso.Ruleset.amodel.parser;
import com.edplan.nso.Ruleset.amodel.object.HitObject;
import com.edplan.nso.NsoException;

public interface HitObjectParser<T extends HitObject>
{
	public T parse(String res) throws NsoException;
}
