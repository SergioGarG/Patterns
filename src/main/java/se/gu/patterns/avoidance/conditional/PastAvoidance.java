package se.gu.patterns.avoidance.conditional;

import com.google.inject.internal.util.Preconditions;

import se.gu.ltl.atoms.LTLPEAtom;
import se.gu.ltl.atoms.LTLPLAtom;
import se.gu.patterns.Pattern;
import se.gu.patterns.visitor.PatternVisitor;

public class PastAvoidance extends Pattern {

	private final  LTLPEAtom condition;
	private final LTLPLAtom locationCondition;
	
	public PastAvoidance(LTLPEAtom condition, LTLPLAtom locationCondition) {
		Preconditions.checkNotNull(condition, "The environment condition cannot be null");
		Preconditions.checkNotNull(locationCondition, "The location condition cannot be null");
		this.condition=condition;
		this.locationCondition=locationCondition;
		
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
	 * @return the locationCondition
	 */
	public LTLPLAtom getLocationCondition() {
		return locationCondition;
	}

}
