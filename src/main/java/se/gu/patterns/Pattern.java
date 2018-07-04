package se.gu.patterns;

import se.gu.patterns.visitor.PatternVisitor;

public abstract class Pattern {
	
	public abstract <T> T accept(PatternVisitor<T> visitor);


}
