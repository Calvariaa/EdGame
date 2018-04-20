package com.edplan.nso.ruleset.amodel.parser;
import com.edplan.nso.ruleset.amodel.object.HitObject;
import com.edplan.nso.NsoException;

public interface HitObjectParser<T extends HitObject>
{
	public T parse(String res) throws NsoException;
}
