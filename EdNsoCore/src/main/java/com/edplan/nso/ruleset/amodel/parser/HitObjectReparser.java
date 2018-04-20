package com.edplan.nso.ruleset.amodel.parser;
import com.edplan.nso.ruleset.amodel.object.HitObject;
import com.edplan.nso.NsoException;

public interface HitObjectReparser<T extends HitObject>
{
	public String reparse(T t) throws NsoException;
}
