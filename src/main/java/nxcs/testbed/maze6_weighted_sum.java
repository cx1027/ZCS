//package nxcs.testbed;
//
//import nxcs.*;
//import nxcs.common.MazeBase;
//import nxcs.stats.StepSnapshot;
//
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class maze6_weighted_sum extends MazeBase {
//
//    /**
//     * Loads a maze from the given maze file
//     *
//     * @param mazeFile The filename of the maze to load
//     * @throws IOException On standard IO problems
//     */
//    public maze6_weighted_sum(String mazeFile) throws IOException {
//        super(new File(mazeFile));
//    }
//
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    /* return reward and action */
//    public ActionPareto getReward(String state, int action) {
//        stepCount++;
//        ActionPareto reward = new ActionPareto(new Qvector(-1, 0), 1);
//
//        try {
//            this.move(action);
//            if (this.isEndOfProblem(this.getState()))
//                reward = this.currentPositionReward.get(new Point(this.x, this.y));
//        } catch (Exception e) {
//            logger.info(String.format("#####Fatal error: %s  %d", state, action));
//            throw e;
//        }
//
//        return reward;
//    }
//
//    public void move(int action)
//    {
//        super.move(action);
//        if (stepCount > 100) {
//            Point p = this.getCurrentLocation();
//            this.resetPosition();
//            logger.info(String.format("Cannot go to final state from: %s after 100 steps, reset to random position:%s", p, this.getCurrentLocation()));
//        }
//    }
//    public ArrayList<ArrayList<StepSnapshot>> getOpenLocationExpectPaths() {
//        ArrayList<ArrayList<StepSnapshot>> expect = new ArrayList<ArrayList<StepSnapshot>>();
//        ArrayList<StepSnapshot> e11 = new ArrayList<StepSnapshot>();
//
//
//        return expect;
//    }
//
//
//    @Override
//    public boolean isTraceConditionMeet() {
//        return (this.finalStateCount % this.mp.resultInterval == 0)
//                || (this.mp.logLowerFinalState && ((this.finalStateCount < 5)
//                || (this.finalStateCount < 20 && this.finalStateCount % 5 == 0)
//                || (this.finalStateCount < 100 && this.finalStateCount % 10 == 0)))
//                ;
//    }
//
//    @Override
//    public List<double[]> getTraceWeight(List<double[]> traceWeights) {
//        List<double[]> ret = new ArrayList<double[]>();
//        //traceWeights.clear();
////        traceWeights.add(new double[]{0.0d, 1.0d});
////        traceWeights.add(new double[]{0.56d, 0.44d});
////        traceWeights.add(new double[]{1.0d, 0.0d});
//        ret.add(traceWeights.get(0));
//        ret.add(traceWeights.get(12));
//        ret.add(traceWeights.get(1));
//        ret.add(traceWeights.get(13));
//        ret.add(traceWeights.get(traceWeights.size()-2));
//        ret.add(traceWeights.get(traceWeights.size()-1));
//        return ret;//traceWeights;
//    }
//}