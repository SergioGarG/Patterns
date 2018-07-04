package se.gu.patterns.avoidance.restricted;

import com.google.inject.internal.util.Preconditions;

import se.gu.ltl.atoms.LTLPLAtom;
import se.gu.patterns.Pattern;
import se.gu.patterns.visitor.PatternVisitor;

public class LowerRestrictedAvoidance extends Pattern{

	private final LTLPLAtom locationCondition;
	private final int num;
	
	
	public LowerRestrictedAvoidance(LTLPLAtom locationCondition, int num) {
		Preconditions.checkNotNull(locationCondition, "The location condition cannot be null");
		this.locationCondition = locationCondition;
		this.num=num;

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

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
}
