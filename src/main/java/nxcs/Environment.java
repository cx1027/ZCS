package nxcs;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An interface that represents an Environment that an NXCS instance
 * can operate on.
 */
public interface Environment {
    /**
     * Gets the current state of this environment. NOTE: This method
     * <i>should not</i> change anything in the environment
     *
     * @return The current state of this environment
     */
    public String getState();

    /**
     * Calculates and returns the reward of performing the given action in the given state,
     * possibly updating the state.
     *
     * @param state  The state to perform the action in
     * @param action The action to perform
     * @return The reward of performing the given action in the given state
     */
    public ActionPareto getReward(String state, int action);

//	public ActionPareto getReward1(String state, int action, double first_reward, int finalStateCount, maze1_weighted_sum maze, Point weight, NXCS nxcs, NXCSParameters params);


    public void printOpenLocationClassifiers(int timestamp, NXCS nxcs, List<double[]> weightList, double obj_r1, int method);


    /**
     * Checks whether the given state is a final state in this environment. NOTE: This method
     * <i>should not</i> change anything in the environment
     *
     * @param state The state to check
     * @return True if the given state is a final state
     */
    public boolean isEndOfProblem(String state);

    public String getStringForState(int x, int y);

    public ArrayList<Point> getOpenLocations();

    public void resetPosition();

    public void resetToSamePosition(Point location);
//
//    public ArrayList<StepSnapshot> GetTestingPAResultInCSV(int experiment_num, int timestamp, NXCS nxcs, double[] weight, double obj_r1, int[] ActionSelect);
//
//    public ArrayList<StepSnapshot> GetTrainingPAResultInCSV(int experiment_num, int timestamp, NXCS nxcs, double[] weight, double obj_r1);

    public Point getCurrentLocation();

    public List<Integer> getActions();
}
