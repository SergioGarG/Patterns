package se.gu.patterns.visitor;

import java.util.List;

import com.google.common.collect.Lists;

import se.gu.ltl.LTLEventually;
import se.gu.ltl.LTLFormula;
import se.gu.ltl.LTLGlobally;
import se.gu.ltl.LTLImplies;
import se.gu.ltl.LTLNeg;
import se.gu.ltl.LTLUntil;
import se.gu.ltl.LTLWeakUntil;
import se.gu.ltl.LTLNext;
import se.gu.ltl.atoms.LTLPLAtom;
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

public class Pattern2LTL implements PatternVisitor<LTLFormula> {

	@Override
	public LTLFormula visit(Visit ltlpaAtom) {

		LTLFormula init = LTLFormula.TRUE;
		for (LTLFormula atom : ltlpaAtom.getAtoms()) {
			init = LTLFormula.getAnd(init, new LTLEventually(atom));
		}
		return init;
	}

	@Override
	public LTLFormula visit(SequencedVisit sequencedVisit) {

		LTLFormula init = LTLFormula.TRUE;
		List<LTLPLAtom> invertedList = sequencedVisit.getAtoms();
		invertedList = Lists.reverse(invertedList);

		for (LTLFormula atom : invertedList) {
			init = LTLFormula.getAnd(atom, new LTLEventually(init));
		}
		return init;

	}

	@Override
	public LTLFormula visit(OrderedVisit orderedVisit) {

		LTLFormula f1 = LTLFormula.TRUE;
		List<LTLPLAtom> invertedList = orderedVisit.getAtoms();
		invertedList = Lists.reverse(invertedList);

		for (LTLFormula atom : invertedList) {
			f1 = LTLFormula.getAnd(atom, new LTLEventually(f1));
		}

		LTLFormula f2 = LTLFormula.TRUE;
		List<LTLPLAtom> orderedList = orderedVisit.getAtoms();

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f2 = LTLFormula.getAnd(f2, new LTLUntil(new LTLNeg(orderedList.get(i + 1)), orderedList.get(i)));
		}

		return LTLFormula.getAnd(f1, f2);
	}

	@Override
	public LTLFormula visit(StrictOrderedVisit strictOrderedVisit) {
		LTLFormula f1 = LTLFormula.TRUE;
		List<LTLPLAtom> invertedList = strictOrderedVisit.getAtoms();
		invertedList = Lists.reverse(invertedList);

		for (LTLFormula atom : invertedList) {
			f1 = LTLFormula.getAnd(atom, new LTLEventually(f1));
		}

		LTLFormula f2 = LTLFormula.TRUE;
		List<LTLPLAtom> orderedList = strictOrderedVisit.getAtoms();

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f2 = LTLFormula.getAnd(f2, new LTLUntil(new LTLNeg(orderedList.get(i + 1)), orderedList.get(i)));
		}

		LTLFormula f3 = LTLFormula.TRUE;

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f3 = LTLFormula.getAnd(f3,
					new LTLUntil(new LTLNeg(orderedList.get(i)), LTLFormula.getAnd(orderedList.get(i),
							new LTLNext(new LTLUntil(new LTLNeg(orderedList.get(i)), orderedList.get(i + 1))))));

		}

		return LTLFormula.getAnd(f1, f2, f3);
	}

	@Override
	public LTLFormula visit(FairVisit fairVisit) {

		LTLFormula f1 = LTLFormula.TRUE;

		for (LTLFormula atom : fairVisit.getAtoms()) {
			f1 = LTLFormula.getAnd(f1, new LTLEventually(atom));
		}

		LTLFormula f2 = LTLFormula.TRUE;
		List<LTLPLAtom> orderedList = fairVisit.getAtoms();

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f2 = LTLFormula.getAnd(f2, new LTLImplies(orderedList.get(i),
					new LTLWeakUntil(new LTLNeg(orderedList.get(i)), orderedList.get(i + 1))));
		}

		f2 = LTLFormula.getAnd(f2, new LTLImplies(orderedList.get(orderedList.size()),
				new LTLWeakUntil(new LTLNeg(orderedList.get(orderedList.size())), orderedList.get(0))));

		return LTLFormula.getAnd(f1, f2);
	}

	@Override
	public LTLFormula visit(Patrolling patrolling) {
		LTLFormula f1 = LTLFormula.TRUE;
		List<LTLPLAtom> list = patrolling.getAtoms();

		for (LTLFormula atom : list) {
			f1 = LTLFormula.getAnd(atom, new LTLEventually(f1));
		}
		return new LTLGlobally(f1);

	}

	@Override
	public LTLFormula visit(SequencedPatrolling sequencedPatrolling) {
		LTLFormula f1 = LTLFormula.TRUE;
		List<LTLPLAtom> invertedList = sequencedPatrolling.getAtoms();
		invertedList = Lists.reverse(invertedList);

		for (LTLFormula atom : invertedList) {
			f1 = LTLFormula.getAnd(atom, new LTLEventually(f1));
		}

		return new LTLGlobally(f1);
	}

	@Override
	public LTLFormula visit(OrderedPatrolling orderedPatrolling) {
		LTLFormula f1 = LTLFormula.TRUE;
		List<LTLPLAtom> invertedList = orderedPatrolling.getAtoms();
		invertedList = Lists.reverse(invertedList);

		for (LTLFormula atom : invertedList) {
			f1 = LTLFormula.getAnd(atom, new LTLEventually(f1));
		}

		LTLFormula f2 = LTLFormula.TRUE;
		List<LTLPLAtom> orderedList = orderedPatrolling.getAtoms();

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f2 = LTLFormula.getAnd(f2, new LTLUntil(new LTLNeg(orderedList.get(i + 1)), orderedList.get(i)));
		}

		LTLFormula f3 = LTLFormula.TRUE;

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f3 = LTLFormula.getAnd(f3, new LTLGlobally(new LTLImplies(orderedList.get(i + 1),
					new LTLUntil(new LTLNeg(orderedList.get(i + 1)), orderedList.get(i)))));
		}

		f3 = LTLFormula.getAnd(f3, new LTLGlobally(new LTLImplies(orderedList.get(orderedList.size()),
				new LTLUntil(new LTLNeg(orderedList.get(orderedList.size())), orderedList.get(0)))));

		return LTLFormula.getAnd(new LTLGlobally(f1), f2, f3);
	}

	@Override
	public LTLFormula visit(StrictOrderedPatrolling strictOrderedPatrolling) {
		LTLFormula f1 = LTLFormula.TRUE;
		List<LTLPLAtom> invertedList = strictOrderedPatrolling.getAtoms();
		invertedList = Lists.reverse(invertedList);

		for (LTLFormula atom : invertedList) {
			f1 = LTLFormula.getAnd(atom, new LTLEventually(f1));
		}

		LTLFormula f2 = LTLFormula.TRUE;
		List<LTLPLAtom> orderedList = strictOrderedPatrolling.getAtoms();

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f2 = LTLFormula.getAnd(f2, new LTLUntil(new LTLNeg(orderedList.get(i + 1)), orderedList.get(i)));
		}

		LTLFormula f3 = LTLFormula.TRUE;

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f3 = LTLFormula.getAnd(f3, new LTLGlobally(new LTLImplies(orderedList.get(i + 1),
					new LTLUntil(new LTLNeg(orderedList.get(i + 1)), orderedList.get(i)))));
		}

		f3 = LTLFormula.getAnd(f3, new LTLGlobally(new LTLImplies(orderedList.get(0),
				new LTLUntil(new LTLNeg(orderedList.get(0)), orderedList.get(orderedList.size())))));

		LTLFormula f4 = LTLFormula.TRUE;

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f4 = LTLFormula.getAnd(f4, new LTLGlobally(new LTLImplies(orderedList.get(i),
					new LTLUntil(new LTLNeg(orderedList.get(i)), orderedList.get(i + 1)))));
		}

		f4 = LTLFormula.getAnd(f4, new LTLGlobally(new LTLImplies(orderedList.get(orderedList.size()),
				new LTLUntil(new LTLNeg(orderedList.get(orderedList.size())), orderedList.get(0)))));

		return LTLFormula.getAnd(new LTLGlobally(f1), f2, f3);
	}

	@Override
	public LTLFormula visit(FairPatrolling fairPatrolling) {
		LTLFormula f1 = LTLFormula.TRUE;

		for (LTLFormula atom : fairPatrolling.getAtoms()) {
			f1 = LTLFormula.getAnd(f1, new LTLGlobally(new LTLEventually(atom)));
		}

		LTLFormula f2 = LTLFormula.TRUE;
		List<LTLPLAtom> orderedList = fairPatrolling.getAtoms();

		for (int i = 0; i < orderedList.size() - 1; i++) {
			f2 = LTLFormula.getAnd(f2, new LTLImplies(orderedList.get(i),
					new LTLWeakUntil(new LTLNeg(orderedList.get(i)), orderedList.get(i + 1))));
		}

		f2 = LTLFormula.getAnd(f2, new LTLImplies(orderedList.get(orderedList.size()),
				new LTLWeakUntil(new LTLNeg(orderedList.get(orderedList.size())), orderedList.get(0))));

		return LTLFormula.getAnd(f1, f2);
	}

	@Override
	public LTLFormula visit(FutureAvoidance futureAvoidance) {
		return new LTLGlobally(new LTLImplies(futureAvoidance.getCondition(),
				new LTLGlobally(new LTLNeg(futureAvoidance.getLocationCondition()))));
	}

	@Override
	public LTLFormula visit(PastAvoidance pastAvoidance) {
		return new LTLUntil(new LTLNeg(pastAvoidance.getLocationCondition()), pastAvoidance.getCondition());
	}

	@Override
	public LTLFormula visit(GlobalAvoidance globalAvoidance) {
		return new LTLGlobally(new LTLNeg(globalAvoidance.getLocationCondition()));
	}

	@Override
	public LTLFormula visit(UpperRestrictedAvoidance upperRestrictedAvoidance) {
		
		LTLFormula f1 = new LTLEventually(upperRestrictedAvoidance.getLocationCondition());
		for(int i=0;i<upperRestrictedAvoidance.getNum(); i++)
	
			f1=LTLFormula.getAnd(upperRestrictedAvoidance.getLocationCondition(), new LTLNext(f1));
		return f1;
	}

	@Override
	public LTLFormula visit(LowerRestrictedAvoidance lowerRestrictedAvoidance) {
		
		LTLFormula f1 = new LTLEventually(lowerRestrictedAvoidance.getLocationCondition());
		for(int i=0;i<lowerRestrictedAvoidance.getNum()-1; i++)
	
			f1=LTLFormula.getAnd(lowerRestrictedAvoidance.getLocationCondition(), new LTLNext(f1));
		return f1;
	}

	@Override
	public LTLFormula visit(ExactRestrictedAvoidance exactRestrictedAvoidance) {
		
		LTLFormula f1 = new LTLGlobally(new  LTLNeg(exactRestrictedAvoidance.getLocationCondition()));
		for(int i=0;i<exactRestrictedAvoidance.getNum(); i++) {
			f1=new LTLUntil(new LTLNeg(exactRestrictedAvoidance.getLocationCondition()), LTLFormula.getAnd(exactRestrictedAvoidance.getLocationCondition(),new LTLNext(f1)));
		}
		return f1;
	}

	@Override
	public LTLFormula visit(DelayedReaction delayedReaction) {
		return new LTLGlobally(
				new LTLImplies(delayedReaction.getCondition(), new LTLEventually(delayedReaction.getLocation())));
	}

	@Override
	public LTLFormula visit(InstantaneousReaction instantaneousReaction) {
		if (instantaneousReaction.getLocation() != null) {
			return new LTLGlobally(
					new LTLImplies(instantaneousReaction.getCondition(), instantaneousReaction.getLocation()));
		} else {
			return new LTLGlobally(new LTLImplies(instantaneousReaction.getCondition(),
					instantaneousReaction.getPattern().accept(this)));
		}
	}

	@Override
	public LTLFormula visit(Wait wait) {
		return new LTLUntil(wait.getLocation(), wait.getCondition());
	}

}
