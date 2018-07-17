package nxcs;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * a classifier in XCS in that we have only added the `theta` field below. Note
 * that most of the methods in this class have a default access modifier - package private
 * for nicer encapsulation.
 */
public class Classifier implements Serializable {
    private static final long serialVersionUID = 1L;

    //moead_strength
    public double moeadStrengh;


    public double getMoeadStrengh(double[] MOEAD_Weights){
        return MOEAD_Weights[0]*this.prediction[0]+MOEAD_Weights[1]*this.prediction[1];
    }

    /**
     * The global ID of classifiers. This is used to give
     * each classifier an individual "name"
     */
    public static int GLOBAL_ID = 1;

    /**
     * The ID of this classifier
     */
    public int id;

    /**
     * The action this classifier recommends
     */
    public int action;

    /**
     * The reward prediction of a classifier
     */
    public double[] prediction = new double[2];//TOCHECK

    public double[] predictionNor = new double[2];

    /**
     * The reward error prediction of a classifier
     */
    public double[] error = new double[2];

    /**
     * The reward error prediction of a classifier
     */
    public double[] errorNor = new double[2];
    /**
     * The fitness of the classifier
     */
    public double[] fitnessArray = new double[2];

    public double fitness;

    /**
     * The policy parameter of the classifier
     */
    public double omega;

    /**
     * The experience (in timesteps) of this classifier.
     */
    public int experience;

    /**
     * The timestamp of the last time the GA was run on a set this classifier was in
     */
    public int timestamp;

    /**
     * The average size of the action set this classifier was in
     */
    public double averageSize;

    /**
     * The numerosity of the classifier. This is the number of micro-classifier this macro-classifier represents.
     */
    public int numerosity;

    /**
     * The condition of this classifier, made up a binary string with '#' wildcards
     */
    public String condition;

    public double[] conditionList = {0, 0, 0, 0};


    public double[] weight_moead;
    public double xaxis_L;
    public double yaxis_L;
    public double xaxis_U;
    public double yaxis_U;


    /*
            * Weights from MOEAD
            * */
    public double[] getWeight_moead() {
        return weight_moead;
    }

    public void setWeight_moead(double[] weight_moead) {
        this.weight_moead = weight_moead;
    }

    public double getInverseStrength0() {
        return 1.0 / prediction[0];
    }

    public double getInverseStrength1() {
        return 1.0 / prediction[1];
    }

    /**
     * Construct a classifier with the default values, building a random condition
     *
     * @param params The parameters to use when building the classifier
     */
    public Classifier(NXCSParameters params) {
        id = GLOBAL_ID;
        GLOBAL_ID++;

        //Set up the default settings
        action = XienceMath.randomInt(params.numActions);
        prediction[0] = params.initialPrediction;
        error[0] = params.initialError;
        fitnessArray[0] = params.initialFitness;
        prediction[1] = params.initialPrediction;
        error[1] = params.initialError;
        fitnessArray[1] = params.initialFitness;
        fitness = params.initialFitness;
        omega = params.initialOmega;
        experience = 0;
        timestamp = 0;
        averageSize = 1;
        numerosity = 1;

        //Build the condition
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < params.stateLength; i++) {
            if (XienceMath.random() < params.pHash) {
                build.append('#');
            } else if (XienceMath.random() < 0.5) {
                build.append('0');
            } else {
                build.append('1');
            }
        }
        condition = build.toString();
    }

    /**
     * Constructs a classifier with the default values, building the condition
     * from the given state (For covering)
     *
     * @param params The parameters to use when building the classifier
     * @param state  The state that the condition of this classifier should match
     */
    public Classifier(NXCSParameters params, String state) {
        id = GLOBAL_ID;
        GLOBAL_ID++;

        //Set up the default settings
        action = XienceMath.randomInt(params.numActions);
        prediction[0] = params.initialPrediction; //TODO:CHECK
        error[0] = params.initialError;
        fitnessArray[0] = params.initialFitness;
        prediction[1] = params.initialPrediction;
        error[1] = params.initialError;
        fitnessArray[1] = params.initialFitness;
        fitness = params.initialFitness;
        omega = params.initialOmega;
        experience = 0;
        timestamp = 0;
        averageSize = 1;
        numerosity = 1;

        //Build from the state
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < params.stateLength; i++) {
            if (XienceMath.random() < params.pHash) {
                build.append('#');
            } else {
                build.append(state.charAt(i));
            }
        }
        condition = build.toString();
    }


    public Classifier(NXCSParameters params, String state, Point openlocation) {
        id = GLOBAL_ID;
        GLOBAL_ID++;

        //Set up the default settings
        action = XienceMath.randomInt(params.numActions);
        prediction[0] = params.initialPrediction; //TODO:CHECK
        error[0] = params.initialError;
        fitnessArray[0] = params.initialFitness;
        prediction[1] = params.initialPrediction;
        error[1] = params.initialError;
        fitnessArray[1] = params.initialFitness;
        fitness = params.initialFitness;
        omega = params.initialOmega;
        experience = 0;
        timestamp = 0;
        averageSize = 1;
        numerosity = 1;

        //Build from the state
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < params.stateLength; i++) {
            if (XienceMath.random() < params.pHash) {
                build.append('#');
            } else {
                build.append(state.charAt(i));
            }
        }
        condition = build.toString();

        xaxis_U = openlocation.getX() + rangeX();
        xaxis_L = openlocation.getX() - rangeX();
        yaxis_U = openlocation.getY() + rangeY();
        yaxis_L = openlocation.getY() - rangeY();

        if (Double.isNaN((this.fitnessArray[1]))) {
            System.out.println("aaa");
        }

    }

    public double rangeX() {
        double rX = 0;
        rX = 10 * 0.05 * XienceMath.random();
        return rX;
    }

    public double rangeY() {
        double rY = 0;
        rY = 11 * 0.05 * XienceMath.random();
        return rY;
    }

    /**
     * Mutates this classifier based on the given values, reconstructing the condition
     * based on the given state and possibly changing the action.
     *
     * @param state      The state to mutate with. This mutation ensures that the condition
     *                   of this classifier still matches this state
     * @param numActions The number of actions in the system, so that we can choose a new one
     *                   if necessary
     * @see NXCSParameters#mutationRate
     * @see NXCSParameters#numActions
     */
    void mutate(String state, double mutationRate, int numActions) {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < state.length(); i++) {
            if (XienceMath.random() < mutationRate) {
                if (condition.charAt(i) == '#') {
                    build.append(state.charAt(i));
                } else {
                    build.append('#');
                }
            } else {
                build.append(condition.charAt(i));
            }
        }

        condition = build.toString();

        if (XienceMath.random() < mutationRate) {
            action = XienceMath.randomInt(numActions);
        }
    }

    void mutateInt(double mutationRate) {
        int mark = XienceMath.randomInt(7);
        if (XienceMath.random() < mutationRate) {
            if (mark == 0) {
                this.xaxis_L += rangeX();
            } else if (mark == 1) {
                this.xaxis_L -= rangeX();
            } else if (mark == 2) {
                this.xaxis_U += rangeX();
            } else if (mark == 3) {
                this.xaxis_U -= rangeX();
            } else if (mark == 4) {
                this.yaxis_L += rangeY();
            } else if (mark == 5) {
                this.yaxis_L -= rangeY();
            } else if (mark == 6) {
                this.yaxis_U += rangeY();
            } else if (mark == 7) {
                this.yaxis_U -= rangeY();
            }
            //dont have update action as action already mutation with condition
        }
    }

    /**
     * Calculates the vote for this classifier to be deleted
     *
     * @param averageFitness The average fitness in the population
     *                       of classifiers
     * @return The vote from this classifier for its deletion
     * @see NXCSParameters#thetaDel
     * @see NXCSParameters#delta
     */
    //UPDATE DELETE VOTE
    double deleteVote(double averageFitness, int thetaDel, double delta) {
        double vote = averageSize * numerosity;
        if (experience > thetaDel && fitness / numerosity < delta * averageFitness) {
            return vote * averageFitness / (fitness / numerosity);
        }
        return vote;
    }

//    double deleteVoteByError(double averageError, int thetaDel, double delta) {
//        double vote = averageSize * numerosity;
//        if (experience > thetaDel && ((error[0] + error[1]) / 2) / numerosity < delta * averageError) {
//            return vote * averageError / (((fitnessArray[0] + fitnessArray[1]) / 2) / numerosity);
//        }
//        return vote;
//    }

    /**
     * Returns whether this classifier has the requirements to subsume others
     *
     * @return True if this classifier can subsume others, false otherwise
     * @see NXCSParameters#thetaSub
     * @see NXCSParameters#e0
     */
    //TODO:update couldSubsume
    boolean couldSubsume(double thetaSub, double e0) {
        return experience > thetaSub && ((predictionNor[0] + predictionNor[1]) / 2) > e0;
    }

    /**
     * Returns whether this classifier is more general than the other. That is,
     * it has more wildcards, and their conditions match.
     *
     * @param other The classifier to check this classifier is more general than
     * @return True if this classifier is more general than the other
     */
    boolean isMoreGeneral(Classifier other) {
        long selfWildcards = condition.chars().filter(c -> c == '#').count();
        long otherWildcards = other.condition.chars().filter(c -> c == '#').count();

        if (selfWildcards <= otherWildcards) {
            return false;
        }

        return IntStream.range(0, condition.length()).allMatch(i -> condition.charAt(i) == '#' || condition.charAt(i) == other.condition.charAt(i));
    }

    /**
     * Returns whether this classifier can subsume the given one. That is, it has the ability to subsume,
     * and it is more general than the other.
     *
     * @param other The classifier to check that this can subsume
     * @return True if this classifier can subsume the other, false otherwise
     * @see NXCSParameters#thetaSub
     * @see NXCSParameters#e0
     */
    boolean doesSubsume(Classifier other, int thetaSub, double e0) {
        return action == other.action && Arrays.equals(weight_moead, other.weight_moead) && couldSubsume(thetaSub, e0) && isMoreGeneral(other);
    }

    /**
     * Performs a deepclone of this Classifier, returning the new Classifier
     *
     * @return The classifier which is an exact clone of this (barring the ID)
     */
    Classifier deepcopy() {
        //Basically we serialize this and then deserialize into a new object
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();

            final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            Classifier cl = (Classifier) ois.readObject();
            cl.id = GLOBAL_ID;
            GLOBAL_ID++;
            return cl;
        } catch (final Exception e) {
            throw new RuntimeException("Cloning failed");
        }
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Classifier)) return false;
        Classifier clas = (Classifier) other;

        return clas.id == id;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(String.format("Classifier:%d [%s = %d, Numerosity: %d, weight:%f,%f, experienct:%d, bound:%f,%f,%f,%f", id, condition, action, numerosity, weight_moead[0], weight_moead[1], experience, xaxis_L, xaxis_U, yaxis_L, yaxis_U));
        for (int i = 0; i < error.length; i++) {//TODO:
            build.append(String.format(", fitnessArray: %3.2f, Error: %3.2f, ErrorNor: %3.2f, Prediction: %3.2f", fitnessArray[i], error[i], predictionNor[i], prediction[i]));
        }
        build.append("]\n");

        return build.toString();
    }

    public Classifier parent;
}
