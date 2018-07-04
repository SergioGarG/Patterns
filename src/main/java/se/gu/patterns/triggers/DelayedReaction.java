package se.gu.patterns.triggers;

import se.gu.ltl.atoms.LTLPEAtom;
import se.gu.ltl.atoms.LTLPLAtom;
import se.gu.patterns.Pattern;
import se.gu.patterns.visitor.PatternVisitor;

public class DelayedReaction extends Pattern {

	private final  LTLPEAtom condition;
	private final LTLPLAtom location;
	private final Pattern pattern;
	
	public DelayedReaction(LTLPEAtom condition, LTLPLAtom location, Pattern pattern) {
		
		this.condition=condition;
		this.location=location;
		this.pattern=pattern;
		
	}

	@Override
	public <T> T accept(PatternVisitor<T> visitor) {
		return visitor.visit(this);
	}

	/**
	 * @return the condition
	 */
	public LTLPEAtom getCondition() {
		return condition;
	}

	/**
	 * @return the location
	 */
	public LTLPLAtom getLocation() {
		return location;
	}

	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}
	
}
