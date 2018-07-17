package nxcs.common;

import java.util.List;

public interface ITrace {
    boolean isTraceConditionMeet();
    List<double[]> getTraceWeight(List<double[]> weights);
}
