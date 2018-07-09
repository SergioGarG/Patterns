package se.gu.patterns;

import java.util.List;

import se.gu.ltl.atoms.LTLPLAtom;
import se.gu.patterns.visitor.PatternVisitor;

public abstract class Pattern {
	
	public abstract <T> T accept(PatternVisitor<T> visitor);

;

}
