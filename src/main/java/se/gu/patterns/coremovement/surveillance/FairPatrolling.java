package se.gu.patterns.coremovement.surveillance;

import java.util.Set;

import com.google.common.base.Preconditions;

import se.gu.ltl.atoms.LTLPLAtom;
import se.gu.patterns.Pattern;
import se.gu.patterns.visitor.PatternVisitor;

public class FairPatrolling extends Pattern{
	
	private final Set<LTLPLAtom> atoms;
	
	public FairPatrolling(Set<LTLPLAtom> atoms) {
		Preconditions.checkNotNull(atoms, "The set of locations to be visited cannot be null");
		
		Preconditions.checkArgument(atoms.size()>0, "At least a location muse be present to create the visit pattern");
		this.atoms=atoms;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T accept(PatternVisitor<T> visitor) {
		return visitor.visit(this);
	}


	/**
	 * @return the atoms
	 */
	public Set<LTLPLAtom> getAtoms() {
		return atoms;
	}


}
