package se.gu.patterns.visitor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import se.gu.patterns.avoidance.conditional.FutureAvoidance;
import se.gu.patterns.avoidance.conditional.GlobalAvoidance;
import se.gu.patterns.avoidance.conditional.PastAvoidance;
import se.gu.patterns.avoidance.restricted.ExactRestrictedAvoidance;
import se.gu.patterns.avoidance.restricted.LowerRestrictedAvoidance;
import se.gu.patterns.avoidance.restricted.UpperRestrictedAvoidance;
import se.gu.patterns.coremovement.coverage.FairVisit;
import se.gu.patterns.coremovement.coverage.OrderedVisit;
import se.gu.patterns.coremovement.coverage.SequencedVisit;
import se.gu.patterns.coremovement.coverage.StrictOrderedVisit;
import se.gu.patterns.coremovement.coverage.Visit;
import se.gu.patterns.coremovement.surveillance.FairPatrolling;
import se.gu.patterns.coremovement.surveillance.OrderedPatrolling;
import se.gu.patterns.coremovement.surveillance.Patrolling;
import se.gu.patterns.coremovement.surveillance.SequencedPatrolling;
import se.gu.patterns.coremovement.surveillance.StrictOrderedPatrolling;
import se.gu.patterns.triggers.DelayedReaction;
import se.gu.patterns.triggers.InstantaneousReaction;
import se.gu.patterns.triggers.Wait;

public class LTLPLAtomVisitor implements PatternVisitor<Set<String>> {

	@Override
	public Set<String> visit(Visit ltlpaAtom) {
		return ltlpaAtom.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(SequencedVisit sequencedVisit) {
		return sequencedVisit.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(OrderedVisit orderedVisit) {
		return orderedVisit.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(StrictOrderedVisit strictOrderedVisit) {
		return strictOrderedVisit.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(FairVisit fairVisit) {
		return fairVisit.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(Patrolling patrolling) {
		return patrolling.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(OrderedPatrolling orderedPatrolling) {
		return orderedPatrolling.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(FairPatrolling fairPatrolling) {
		return fairPatrolling.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(SequencedPatrolling sequencedPatrolling) {
		return sequencedPatrolling.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(StrictOrderedPatrolling strictOrderedPatrolling) {
		return strictOrderedPatrolling.getAtoms().stream().map(s -> s.getLocationName()).collect(Collectors.toSet());
	}

	@Override
	public Set<String> visit(FutureAvoidance futureAvoidance) {
		Set<String> env=new HashSet<>();
		env.add(futureAvoidance.getLocationCondition().getLocationName());
		return env;
	}

	@Override
	public Set<String> visit(PastAvoidance pastAvoidance) {
		Set<String> env=new HashSet<>();
		env.add(pastAvoidance.getLocationCondition().getLocationName());
		return env;
	}

	@Override
	public Set<String> visit(GlobalAvoidance globalAvoidance) {
		Set<String> env=new HashSet<>();
		env.add(globalAvoidance.getLocationCondition().getLocationName());
		return env;
	}

	@Override
	public Set<String> visit(UpperRestrictedAvoidance upperRestrictedAvoidance) {
		Set<String> env=new HashSet<>();
		env.add(upperRestrictedAvoidance.getLocationCondition().getLocationName());
		return env;
	}

	@Override
	public Set<String> visit(LowerRestrictedAvoidance lowerRestrictedAvoidance) {
		Set<String> env=new HashSet<>();
		env.add(lowerRestrictedAvoidance.getLocationCondition().getLocationName());
		return env;
	}

	@Override
	public Set<String> visit(ExactRestrictedAvoidance exactRestrictedAvoidance) {
		Set<String> env=new HashSet<>();
		env.add(exactRestrictedAvoidance.getLocationCondition().getLocationName());
		return env;
	}

	@Override
	public Set<String> visit(DelayedReaction delayedReaction) {
		Set<String> env=new HashSet<>();
		if(delayedReaction.getPattern()!=null) {
			env.addAll(delayedReaction.getPattern().accept(this));
		}
		else {
			env.add(delayedReaction.getLocation().getLocationName());
		}
		return env;
	}

	@Override
	public Set<String> visit(InstantaneousReaction instantaneousReaction) {
		Set<String> env=new HashSet<>();
		if(instantaneousReaction.getPattern()!=null) {
			env.addAll(instantaneousReaction.getPattern().accept(this));
		}
		else {
			env.add(instantaneousReaction.getLocation().getLocationName());
		}
		return env;
	}

	@Override
	public Set<String> visit(Wait wait) {
		Set<String> env=new HashSet<>();
		env.add(wait.getLocation().getLocationName());
		return env;
	}
}
