package se.gu.patterns.visitor;

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

public interface PatternVisitor<T> {

	
	public T visit(Visit ltlpaAtom);

	public T visit(SequencedVisit sequencedVisit);

	public T visit(OrderedVisit orderedVisit);

	public T visit(StrictOrderedVisit strictOrderedVisit);

	public T visit(FairVisit fairVisit);

	public T visit(Patrolling patrolling);

	public T visit(OrderedPatrolling orderedPatrolling);

	public T visit(FairPatrolling fairPatrolling);

	public T visit(SequencedPatrolling sequencedPatrolling);

	public T visit(StrictOrderedPatrolling strictOrderedPatrolling);

	public T visit(FutureAvoidance futureAvoidance);

	public T visit(PastAvoidance pastAvoidance);

	public T visit(GlobalAvoidance globalAvoidance);

	public T visit(UpperRestrictedAvoidance upperRestrictedAvoidance);

	public T visit(LowerRestrictedAvoidance lowerRestrictedAvoidance);

	public T visit(ExactRestrictedAvoidance exactRestrictedAvoidance);

	public T visit(DelayedReaction delayedReaction);

	public T visit(InstantaneousReaction instantaneousReaction);

	public T visit(Wait wait);


}
