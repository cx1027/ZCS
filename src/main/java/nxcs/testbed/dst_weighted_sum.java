
package nxcs.testbed;

import nxcs.*;
import nxcs.common.MazeBase;
import nxcs.stats.StepSnapshot;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

//import nxcs.Trace;


/**
 * Represents a maze problem which is loaded in from a file. Such a file should
 * contain a rectangular array of 'O', 'T' and 'F' characters. A sample is given
 * in Mazes/Woods1.txt
 */

public class dst_weighted_sum extends MazeBase {


    /**
     * Loads a maze from the given maze file
     *
     * @param mazeFile The filename of the maze to load
     * @throws IOException On standard IO problems
     */

    public dst_weighted_sum(String mazeFile) throws IOException {
        super(new File(mazeFile));
    }



    /**
     * {@inheritDoc}
     */
    @Override
    /* return reward and action */
    public ActionPareto getReward(String state, int action) {
        stepCount++;
        ActionPareto reward = new ActionPareto(new Qvector(-1, 0), 1);
        try {
            this.move(action);

            if (this.isEndOfProblem(this.getState()))
                reward = this.currentPositionReward.get(new Point(this.x, this.y));
        } catch (Exception e) {
            logger.info(String.format("%s  %d", state, action));
            throw e;
        }

        return reward;
    }

    public void move(int action)
    {
        super.move(action);

        if (stepCount > 100) {
            Point p = this.getCurrentLocation();
            this.resetPosition();
            logger.info(String.format("Cannot go to final state from: %s after 100 steps, reset to random position:%s", p, this.getCurrentLocation()));

        }
    }

    @Override
    public boolean isTraceConditionMeet() {
        return (this.finalStateCount % this.mp.resultInterval == 0)
                || (this.mp.logLowerFinalState && (
                (this.finalStateCount < 20 && this.finalStateCount % 5 == 0)
                        || (this.finalStateCount < 100 && this.finalStateCount % 10 == 0)))
                ;
    }

    public ArrayList<ArrayList<StepSnapshot>> getOpenLocationExpectPaths(){
        return null;
    }


    @Override
    public List<double[]> getTraceWeight(List<double[]> traceWeights) {
        List<double[]> ret = new ArrayList<double[]>();
        ret.add(traceWeights.get(0));
        ret.add(traceWeights.get(1));
        ret.add(traceWeights.get(traceWeights.size()-1));
        return traceWeights;
    }
}