package se.gu.patterns.avoidance.conditional;

import com.google.inject.internal.util.Preconditions;

import se.gu.ltl.atoms.LTLPLAtom;
import se.gu.patterns.Pattern;
import se.gu.patterns.visitor.PatternVisitor;

public class GlobalAvoidance extends Pattern {

	private final LTLPLAtom locationCondition;

	public GlobalAvoidance(LTLPLAtom locationCondition) {
		Preconditions.checkNotNull(locationCondition, "The location condition cannot be null");
		this.locationCondition = locationCondition;

	}

	@Override
	public <T> T accept(PatternVisitor<T> visitor) {
		return visitor.visit(this);
		
	}

	/**
	 * @return the locationCondition
	 */
	public LTLPLAtom getLocationCondition() {
		return locationCondition;
	}

}
