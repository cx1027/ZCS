package nxcs.stats;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StepSnapshot {
    /***
     * sequence number of experiment trail times
     */
    private int trailNumber;
    /***
     * timestamp for logging
     */
    private int timestamp;
    private Point openState;
    private Point finalState;

    private double first_reward;
    private double[] targtWeight;
    private double objective;
    private double[] weight;
    private int steps;
    private double hyperVolumn;

    private double Q_finalreward_left;
    private double Q_finalreward_right;
    private double Q_finalreward_delta;
    private double Q_finalreward_max;
    private double Q_steps_left;
    private double Q_steps_right;
    private double Q_steps_delta;
    private double Q_steps_min;
    private double Q_total_left;
    private double Q_total_right;
    private double Q_finalreward_select;
    private double Q_steps_select;
    private double Q_total_select;
    private List<Point> path;
    private double[] PA1;
    private double[] PA2;
    private double[] PAtotal;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Point getOpenState() {
        return openState;
    }

    public void setOpenState(Point openState) {
        this.openState = openState;
    }

    public Point getFinalState() {
        return finalState;
    }

    public void setFinalState(Point finalState) {
        this.finalState = finalState;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setHyperVolumn(double hyperVolumn) {
        this.hyperVolumn = hyperVolumn;
    }

    public List<Point> getPath() {
        return path;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }

    public StepSnapshot(int timestamp, Point openState, Point finalState, int steps, ArrayList<Point> path) {
        this.timestamp = timestamp;
        this.openState = openState;
        this.finalState = finalState;
        this.steps = steps;
        this.setPath(path);
    }

    public StepSnapshot(int trailNumber, int timestamp, double[] weight, double first_reward, Point openState, double Q_finalreward_left, double Q_finalreward_right, double Q_finalreward_delta, double Q_finalreward_max, double Q_steps_left, double Q_steps_right, double Q_steps_delta, double Q_steps_min, int steps) {
        this.trailNumber = trailNumber;
        this.timestamp = timestamp;
        this.openState = openState;
        this.weight = weight;
        this.first_reward = first_reward;
        this.Q_finalreward_left = Q_finalreward_left;
        this.Q_finalreward_right = Q_finalreward_right;
        this.Q_finalreward_delta = Q_finalreward_delta;
        this.Q_finalreward_max = Q_finalreward_max;
        this.Q_steps_left = Q_steps_left;
        this.Q_steps_right = Q_steps_right;
        this.Q_steps_delta = Q_steps_delta;
        this.Q_steps_min = Q_steps_min;
        this.steps = steps;
        //this.setPath(path);
    }

    public StepSnapshot(int trailNumber, int finalCount, double[] weight, double first_reward, Point openState, double Q_finalreward_left, double Q_finalreward_right, double Q_finalreward_delta, double Q_finalreward_max, double Q_steps_left, double Q_steps_right, double Q_steps_delta, double Q_steps_min, int steps, double Q_total_left, double Q_total_right, double Q_finalreward_select, double Q_steps_select, double Q_total_select) {
        this(trailNumber, finalCount, weight, first_reward, openState, Q_finalreward_left, Q_finalreward_right, Q_finalreward_delta, Q_finalreward_max, Q_steps_left, Q_steps_right, Q_steps_delta, Q_steps_min, steps);
        this.Q_total_left = Q_total_left;
        this.Q_total_right = Q_total_right;
        this.Q_finalreward_select = Q_finalreward_select;
        this.Q_steps_select = Q_steps_select;
        this.Q_total_select = Q_total_select;
        //this.setPath(path);
    }

    /***
     * constructor for steps, hypervolumn calculation
     * @param trailNumber
     * @param timestamp
     * @param openState
     * @param finalState
     * @param targetWeight
     * @param objective
     * @param weight
     * @param steps
     * @param hyperVolumn
     */
    public StepSnapshot(int trailNumber, int timestamp, Point openState, Point finalState, double[] targetWeight
            , double objective, double[] weight, int steps, double hyperVolumn, List<Point> path
            , double[] PA1, double[] PA2, double[] PAtotal) {
        this.trailNumber = trailNumber;
        this.timestamp = timestamp;
        this.openState = openState;
        this.finalState = finalState;
        this.targtWeight = targetWeight;
        this.objective = objective;
        this.weight = weight;
        this.steps = steps;
        this.hyperVolumn = hyperVolumn;
        this.path = path;
        this.PA1 = PA1;
        this.PA2 = PA2;
        this.PAtotal = PAtotal;
    }

    public StepSnapshot(Point openState, Point finalState, int steps, ArrayList<Point> path) {
        this(0, openState, finalState, steps, path);
    }

    public StepSnapshot(int timestamp, Point openState, Point finalState, int steps) {
        this(timestamp, openState, finalState, steps, null);
    }

    public StepSnapshot(Point openState, Point finalState, int steps) {
        this(0, openState, finalState, steps, null);
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(String.format("%d", this.trailNumber));
        build.append(", ");
        build.append(String.format("%d", this.timestamp));
        build.append(",");
        build.append(String.format("(%d-%d)", (int) this.weight[0], (int) this.weight[1]));
        build.append(", ");
        build.append(String.format("%f", this.first_reward));
        build.append(", ");
        build.append(String.format("(%d-%d)", (int) this.openState.getX(), (int) this.openState.getY()));
        build.append(", ");

        build.append(String.format("%f", this.Q_finalreward_left));
        build.append(", ");
        build.append(String.format("%f", this.Q_finalreward_right));
        build.append(", ");
        build.append(String.format("%f", this.Q_finalreward_delta));
        build.append(", ");
        build.append(String.format("%f", this.Q_finalreward_max));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_left));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_right));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_delta));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_min));
//		build.append(", ");
//
//		if (this.path.size() > 0)
//			for (Point p : this.path) {
//				build.append(String.format("->(%d-%d)", (int) p.getX(), (int) p.getY()));
//			}
//		build.append("->");
        build.append("\n");

        return build.toString();
    }

    public String toCSV() {


        StringBuilder build = new StringBuilder();
        build.append(this.timestamp);
        build.append(", ");
        build.append(String.format("(%d-%d)", (int) this.openState.getX(), (int) this.openState.getY()));
        build.append(", ");
        build.append(String.format("(%d-%d)", (int) this.finalState.getX(), (int) this.finalState.getY()));
        build.append(", ");
        build.append(this.steps);
        build.append(", ");
        if (this.path.size() > 0)
            for (Point p : this.path) {
                build.append(String.format("->(%d-%d)", (int) p.getX(), (int) p.getY()));
            }
        build.append("->");
        build.append("\n");

        return build.toString();
    }

    //TODO:update toCSV
    public String toCSV_PA() {
        //trailNumber, timestamp, weight, obj_r1, p, Q_finalreward_left, Q_finalreward_right, Q_finalreward_delta, Q_finalreward_max, Q_steps_left, Q_steps_right, Q_steps_delta, Q_steps_min);

        StringBuilder build = new StringBuilder();
        build.append(String.format("%d", this.trailNumber));
        build.append(", ");
        build.append(String.format("%d", this.timestamp));
        build.append(", ");
        build.append(String.format("(%d-%d)", (int) this.weight[0], (int) this.weight[1]));
        build.append(", ");
        build.append(String.format("%f", this.first_reward));
        build.append(", ");
        build.append(String.format("(%d-%d)", (int) this.openState.getX(), (int) this.openState.getY()));
        build.append(", ");

        build.append(String.format("%f", this.Q_finalreward_left));
        build.append(", ");
        build.append(String.format("%f", this.Q_finalreward_right));
        build.append(", ");
        build.append(String.format("%f", this.Q_finalreward_delta));
        build.append(", ");
        build.append(String.format("%f", this.Q_finalreward_max));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_left));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_right));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_delta));
        build.append(", ");
        build.append(String.format("%f", this.Q_steps_min));
//		build.append(", ");
//
//		if (this.path.size() > 0)
//			for (Point p : this.path) {
//				build.append(String.format("->(%d-%d)", (int) p.getX(), (int) p.getY()));
//			}
//		build.append("->");
        build.append("\n");

        return build.toString();
    }


    public String to_Total_CSV_PA() {
        //trailNumber, timestamp, TargetWeight,TraceWeight, obj_r1, OpenState, FinalState
        // , Q_finalreward_left, Q_finalreward_right, Q_finalreward_delta, Q_finalreward_max
        // , Q_steps_left, Q_steps_right, Q_steps_delta, Q_steps_min
        // , Q_total_left. Q_total_right, Q_finalreward_select, Q_steps_select, Q_total_select, steps, hyperVolumn);

        StringBuilder build = new StringBuilder();
        build.append(String.format("%d", this.trailNumber));
        build.append(",");
        build.append(String.format("%d", this.timestamp));
        build.append(",");
        build.append(String.format("%f|%f", this.targtWeight[0], this.targtWeight[1]));
        build.append(",");
        build.append(String.format("%f|%f", this.weight[0], this.weight[1]));
        build.append(",");
        build.append(String.format("%f", this.first_reward));
        build.append(",");
        build.append(String.format("(%d-%d)", (int) this.openState.getX(), (int) this.openState.getY()));
        build.append(",");
        build.append(String.format("(%d-%d)", (int) this.finalState.getX(), (int) this.finalState.getY()));
        build.append(",");

//        build.append(String.format("%f", this.Q_finalreward_left));
//        build.append(",");
//        build.append(String.format("%f", this.Q_finalreward_right));
//        build.append(",");
//        build.append(String.format("%f", this.Q_finalreward_delta));
//        build.append(",");
//        build.append(String.format("%f", this.Q_finalreward_max));
//
//        build.append(",");
//        build.append(String.format("%f", this.Q_steps_left));
//        build.append(",");
//        build.append(String.format("%f", this.Q_steps_right));
//        build.append(",");
//        build.append(String.format("%f", this.Q_steps_delta));
//        build.append(",");
//        build.append(String.format("%f", this.Q_steps_min));
//        build.append(",");
//
//        build.append(String.format("%f", this.Q_total_left));
//        build.append(",");
//        build.append(String.format("%f", this.Q_total_right));
//        build.append(",");
//        build.append(String.format("%f", this.Q_finalreward_select));
//        build.append(",");
//        build.append(String.format("%f", this.Q_steps_select));
//        build.append(",");
//        build.append(String.format("%f", this.Q_total_select));

//        build.append(",");
        build.append(String.format("%d", this.steps));
        build.append(",");
        build.append(String.format("%f", this.hyperVolumn));
        build.append(",");

        if (this.path.size() > 0) {
            build.append((this.path.size() > 30 ? path.stream().limit(30).collect(Collectors.toList()) : path).stream().map(p -> String.format("(%d-%d)", (int) p.getX(), (int) p.getY())).collect(Collectors.joining("->")));
        }
        build.append(",");
        for (double d : this.PA1) {
            build.append(String.format("%f", d));
            build.append(",");
        }
        for (double d : this.PA2) {
            build.append(String.format("%f", d));
            build.append(",");
        }
        for (double d : this.PAtotal) {
            build.append(String.format("%f", d));
            build.append(",");
        }

        //append end of line
        build.append("\n");

        return build.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StepSnapshot))
            return false;
        if (obj == this)
            return true;
        StepSnapshot q = (StepSnapshot) obj;
        if (this.openState.equals(q.openState) && this.finalState.equals(q.finalState) && this.steps == q.steps)
            return true;
        else
            return false;
    }
}
