package nxcs.utils;

import com.rits.cloning.Cloner;
import nxcs.ActionPareto;
import nxcs.Qvector;
import nxcs.common.IParetoCalculator;

import java.util.ArrayList;
import java.util.List;

public class ParetoCalculator implements IParetoCalculator {
	private Cloner cloner;

	private List<ArrayList<Double>> candidatelist;

	// public ParetoCalculator(List<ArrayList<Double>> candidatelist) {
	// this.candidatelist = candidatelist;
	// }

	public ParetoCalculator() {
		this.cloner = new Cloner();
	}

	public List<Qvector> getPareto(List<ActionPareto> currParentoCandidate) {
		List<Qvector> archivinglist = new ArrayList<>();
		try {
			// TODO: how to add element
			Qvector c0 = currParentoCandidate.get(0).getPareto();// get first item from
			// candidatelist
			archivinglist.add(c0);// give first item to archivinglist

			// archivinglist.set(0, c0);
			int result;
			int ci = 0;

			for (int i = 1; i < currParentoCandidate.size(); i++) {
				// if (ci == 0) {
				// ci = 1;// c0 is already in archivinglist
				// continue;
				// }
				boolean flag = true;
				// ActionPareto candidate = currParentoCandidate.get(i);//
				ActionPareto candidate = cloner.deepClone(currParentoCandidate.get(i));
				List<Qvector> removeList = new ArrayList<>();
				for (int j = 0; j < archivinglist.size(); j++) {
					Qvector archiving = archivinglist.get(j);
					result = Dominate(candidate.getPareto(), archiving);
					if (result == CANDIDATE_IS_BETTER) {// candidate is non donminate
						// archivinglist.remove(archiving);
						removeList.add(archiving);
					} else if (result == CANDIDATE_IS_SAME_AS_ARCHIVING) {// both non dominate
						continue;
					} else if (result == ARCHIVING_IS_BETTER) {// archiving is non dominate
						flag = false;
						break;
					}
				}
				if (removeList.size() > 0) {
					for (Qvector p : removeList) {
						archivinglist.remove(p);
					}
				}
				if (flag == true) {
					archivinglist.add(candidate.getPareto());
				}
			}

		} catch (Exception ex) {
			System.console().printf("pareto error!" + ex.getMessage());
		}
		return archivinglist;
	}

	public List<ActionPareto> getPareto3(List<ActionPareto> currParentoCandidate) {
		List<ActionPareto> archivinglist = new ArrayList<>();
		try {
			// TODO: how to add element
			ActionPareto c0 = currParentoCandidate.get(0);// get first item from
			// candidatelist
			archivinglist.add(c0);// give first item to archivinglist

			// archivinglist.set(0, c0);
			int result;
			int ci = 0;

			for (int i = 1; i < currParentoCandidate.size(); i++) {
				// if (ci == 0) {
				// ci = 1;// c0 is already in archivinglist
				// continue;
				// }
				boolean flag = true;
				// ActionPareto candidate = currParentoCandidate.get(i);//
				ActionPareto candidate = cloner.deepClone(currParentoCandidate.get(i));
				List<ActionPareto> removeList = new ArrayList<>();
				for (int j = 0; j < archivinglist.size(); j++) {
					ActionPareto archiving = archivinglist.get(j);
					result = Dominate(candidate, archiving);
					if (result == CANDIDATE_IS_BETTER) {// candidate is non donminate
						// archivinglist.remove(archiving);
						removeList.add(archiving);
					} else if (result == CANDIDATE_IS_SAME_AS_ARCHIVING) {// both non dominate
						continue;
					} else if (result == ARCHIVING_IS_BETTER) {// archiving is non dominate
						flag = false;
						break;
					}
				}
				if (removeList.size() > 0) {
					for (ActionPareto p : removeList) {
						archivinglist.remove(p);
					}
				}
				if (flag == true) {
					archivinglist.add(candidate);
				}
			}

		} catch (Exception ex) {
			System.console().printf("pareto error!" + ex.getMessage());
		}
		return archivinglist;
	}

	public List<Qvector> getPareto2(List<Qvector> currParentoCandidate) {
		List<Qvector> archivinglist = new ArrayList<>();
		try {
			// TODO: how to add element
			Qvector c0 = currParentoCandidate.get(0);// get first item from
			// candidatelist
			archivinglist.add(c0);// give first item to archivinglist

			// archivinglist.set(0, c0);
			int result;
			int ci = 0;

			for (int i = 1; i < currParentoCandidate.size(); i++) {
				// if (ci == 0) {
				// ci = 1;// c0 is already in archivinglist
				// continue;
				// }
				boolean flag = true;
				// ActionPareto candidate = currParentoCandidate.get(i);//
				Qvector candidate = cloner.deepClone(currParentoCandidate.get(i));
				List<Qvector> removeList = new ArrayList<>();
				for (int j = 0; j < archivinglist.size(); j++) {
					Qvector archiving = archivinglist.get(j);
					result = Dominate(candidate, archiving);
					if (result == CANDIDATE_IS_BETTER) {// candidate is non donminate
						// archivinglist.remove(archiving);
						removeList.add(archiving);
					} else if (result == CANDIDATE_IS_SAME_AS_ARCHIVING) {// both non dominate
						continue;
					} else if (result == ARCHIVING_IS_BETTER) {// archiving is non dominate
						flag = false;
						break;
					}
				}
				if (removeList.size() > 0) {
					for (Qvector p : removeList) {
						archivinglist.remove(p);
					}
				}
				if (flag == true) {
					archivinglist.add(candidate);
				}
			}

		} catch (Exception ex) {
			System.console().printf("pareto error!" + ex.getMessage());
		}
		return archivinglist;
	}

	public int Dominate(ActionPareto candidate, ActionPareto archiving) {
		int result = 0;
		if ((candidate.getPareto().get(0) > archiving.getPareto().get(0) && candidate.getPareto().get(1) < archiving.getPareto().get(1))
				|| (candidate.getPareto().get(0) < archiving.getPareto().get(0) && candidate.getPareto().get(1) > archiving.getPareto().get(1))) {
			result = CANDIDATE_IS_SAME_AS_ARCHIVING;
		} else if (candidate.getPareto().get(0) >= archiving.getPareto().get(0)
				&& candidate.getPareto().get(1) >= archiving.getPareto().get(1)) {
			result = CANDIDATE_IS_BETTER;
		} else if (candidate.getPareto().get(0) <= archiving.getPareto().get(0)
				&& candidate.getPareto().get(1) <= archiving.getPareto().get(1)) {
			result = ARCHIVING_IS_BETTER;
		}
		return result;
	}
	public int Dominate(Qvector candidate, Qvector archiving) {
		int result = 0;
		if ((candidate.get(0) > archiving.get(0) && candidate.get(1) < archiving.get(1))
				|| (candidate.get(0) < archiving.get(0) && candidate.get(1) > archiving.get(1))) {
			result = CANDIDATE_IS_SAME_AS_ARCHIVING;
		} else if (candidate.get(0) >= archiving.get(0) && candidate.get(1) >= archiving.get(1)) {
			result = CANDIDATE_IS_BETTER;
		} else if (candidate.get(0) <= archiving.get(0) && candidate.get(1) <= archiving.get(1)) {
			result = ARCHIVING_IS_BETTER;
		}
		return result;
	}

}
