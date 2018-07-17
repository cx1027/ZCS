package nxcs.common;

import nxcs.ActionPareto;
import nxcs.Qvector;

import java.util.List;

public interface IParetoCalculator {

    static final int CANDIDATE_IS_SAME_AS_ARCHIVING = 1;
    static final int CANDIDATE_IS_BETTER = 2;
    static final int ARCHIVING_IS_BETTER = 3;

    List<Qvector> getPareto(List<ActionPareto> currParentoCandidate);

    /***
     * check if the archiving vector dominates the candidate
     * @param candidate
     * @param archiving
     * @return 1:both non dominate, 2:candidate is non donminate, 3:archiving is non dominate
     *
     */
    int Dominate(Qvector candidate, Qvector archiving);

    List<ActionPareto> getPareto3(List<ActionPareto> currParentoCandidate);
}
