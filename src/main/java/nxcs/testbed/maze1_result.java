package nxcs.testbed;//package nxcs.testbed;
//
//import java.awt.Point;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.stream.Collectors;
//
//import com.rits.cloning.Cloner;
//
//import nxcs.ActionPareto;
//import nxcs.Classifier;
//import nxcs.Environment;
//import nxcs.utils.HyperVolumn;
//import nxcs.NXCS;
//import nxcs.NXCSParameters;
//import nxcs.PathStep;
//import nxcs.Qvector;
//import nxcs.Result;
//import nxcs.Reward;
////import nxcs.Trace;
//import nxcs.XienceMath;
//import nxcs.distance.*;
//import nxcs.stats.*;
//
///**
// * Represents a maze problem which is loaded in from a file. Such a file should
// * contain a rectangular array of 'O', 'T' and 'F' characters. A sample is given
// * in Mazes/Woods1.txt
// */
//public class maze1_result implements Environment {
//	/**
//	 * The raw characters in the maze
//	 */
//	private char[][] mazeTiles;
//
//	/**
//	 * The current position of the agent in the maze
//	 */
//	public int x, y;
//
//	/**
//	 * The map from characters to their binary encodings used in states and
//	 * conditions
//	 */
//	private Map<Character, String> encodingTable;
//
//	/**
//	 * A list of points representing locations we can safely move the agent to
//	 */
//	private List<Point> openLocations;
//
//	/**
//	 * A list of points representing the final states in the environment
//	 */
//	private List<Point> finalStates;
//
//	private ArrayList<Reward> rewardGrid;
//
//	/**
//	 * A list which maps the indices to (delta x, delta y) pairs for moving the
//	 * agent around the environment
//	 */
//	private List<Point> actions;
//
//	public List<Reward> getRewardGrid() {
//		return rewardGrid;
//	}
//
//	public static List<Integer> act = new ArrayList<Integer>();
//
//	/**
//	 * The number of timesteps since the agent last discovered a final state
//	 */
//	private int count;
//
//	private Cloner cloner;
//
//	private final static List<Snapshot> stats = new ArrayList<Snapshot>();
//
//	/**
//	 * Loads a maze from the given maze file
//	 *
//	 * @param mazeFile
//	 *            The filename of the maze to load
//	 * @throws IOException
//	 *             On standard IO problems
//	 */
//	public maze1_result(String mazeFile) throws IOException {
//		this(new File(mazeFile));
//	}
//
//	/**
//	 * Loads a maze from the given maze file
//	 *
//	 * @param f
//	 *            The file of the maze to load
//	 * @throws IOException
//	 *             On standard IO problems
//	 */
//	public maze1_result(File f) throws IOException {
//		// Set up the encoding table FOR DST
//		encodingTable = new HashMap<Character, String>();
//		encodingTable.put('O', "000");
//		encodingTable.put('T', "110");
//		encodingTable.put(null, "100");// For out of the maze positions
//		encodingTable.put('F', "111");
//		encodingTable.put('N', "011");
//
//		// encodingTable.put('1', "001");
//		// encodingTable.put('3', "011");
//		// encodingTable.put('5', "101");
//		// encodingTable.put('8', "010");
//
//		openLocations = new ArrayList<Point>();
//		finalStates = new ArrayList<Point>();
//		rewardGrid = new ArrayList<Reward>();
//
//		actions = new ArrayList<Point>();
//		actions.add(new Point(0, -1));// Up
//		actions.add(new Point(-1, 0));// Left
//		actions.add(new Point(1, 0));// Right
//		actions.add(new Point(0, 1));// Down
//		// actions.add(new Point(-1, -1));// Up, Left
//		// actions.add(new Point(1, -1));// Up, Right
//		// actions.add(new Point(-1, 1));// Down, Left
//		// actions.add(new Point(1, 1));// Down, Right
//
//		// Load the maze into a char array
//		List<String> mazeLines = new ArrayList<String>();
//		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
//			String line;
//			while ((line = reader.readLine()) != null) {
//				mazeLines.add(line);
//			}
//		}
//		mazeTiles = new char[mazeLines.size()][];
//		for (int i = 0; i < mazeLines.size(); i++) {
//			mazeTiles[i] = mazeLines.get(i).toCharArray();
//			if (i > 0 && mazeTiles[i].length != mazeTiles[1].length) {
//				throw new IllegalArgumentException(
//						String.format("Line %d in file %s is of different length than the others", i + 1, f.getName()));
//			}
//
//			for (int j = 0; j < mazeTiles[i].length; j++) {
//				char c = mazeTiles[i][j];
//				if (!encodingTable.containsKey(c)) {
//					throw new IllegalArgumentException(
//							String.format("Line %d in file %s has an invalid character %c", i + 1, f.getName(), c));
//				}
//
//				if (c == 'O') {
//					openLocations.add(new Point(j, i));
//					rewardGrid.add(new Reward(new Point(j, i), -1, 0));
////				} else if (c != 'O' && c != 'T') {
//				} else if (c == 'F') {
//					finalStates.add(new Point(j, i));
//					rewardGrid.add(new Reward(new Point(j, i), -1, Character.getNumericValue(c)));
//				}
//			}
//
//		}
//
//		System.out.println("openLocations:" + openLocations);
//		System.out.println("finalStates:" + finalStates);
//		System.out.println("rewardGrid:" + rewardGrid);
//	}
//
//	/**
//	 * Resets the agent to a random open position in the environment
//	 */
//	public void resetPosition() {
//		Point randomOpenPoint = XienceMath.choice(openLocations);
//		x = randomOpenPoint.x;
//		y = randomOpenPoint.y;
//		count = 0;
//		// x = 1;
//		// y = 1;
//		// count = 0;
//	}
//
//	public void resetToSamePosition(Point xy) {
//		x = xy.x;
//		y = xy.y;
//		count = 0;
//		// x = 1;
//		// y = 1;
//		// count = 0;
//	}
//
//	/**
//	 * Returns the two-bit encoding for the given position in the maze
//	 *
//	 * @param x
//	 *            The x position in the maze to get
//	 * @param y
//	 *            The y position in the maze to get
//	 * @return The two-bit encoding of the given position
//	 */
//	private String getEncoding(int x, int y) {
//		if (x < 0 || y < 0 || y >= mazeTiles.length || x >= mazeTiles[0].length) {
//			return encodingTable.get(null);
//		} else {
//			return encodingTable.get(mazeTiles[y][x]);
//
//		}
//	}
//
//	/**
//	 * Calculates the 16-bit state for the given position, from the 8 positions
//	 * around it
//	 *
//	 * @param x
//	 *            The x position of the state to get the encoding for
//	 * @param y
//	 *            The y position of the state to get the encoding for
//	 * @return The binary representation of the given state
//	 */
//	private String getStringForState(int x, int y) {
//		StringBuilder build = new StringBuilder();
//		for (int dy = -1; dy <= 1; dy++) {
//			for (int dx = -1; dx <= 1; dx++) {
//				if (dx == 0 && dy == 0)
//					continue;
//				build.append(getEncoding(x + dx, y + dy));
//			}
//		}
//		return build.toString();
//	}
//
//	/**
//	 * Checks whether the given position is a valid position that the agent can
//	 * be in in this maze. A position is valid if it is inside the bounds of the
//	 * maze and is not a tree (T)
//	 *
//	 * @param x
//	 *            The x position to check
//	 * @param y
//	 *            The y position to check.
//	 * @return True if the given (x, y) position is a valid position in the maze
//	 */
//	private boolean isValidPosition(int x, int y) {
//		return !(x < 0 || y < 0 || y >= mazeTiles.length || x >= mazeTiles[0].length) && mazeTiles[y][x] != 'T';
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public String getState() {
//		System.out.println(String.format("x,y:%d %d", x, y));
//		return getStringForState(x, y);
//	}
//
//	public Point getCurrentLocation() {
//		return new Point(x, y);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	/* return reward and action */
//
//	public ActionPareto getReward(String state, int action, double first_reward) {
//		count++;
//		ActionPareto reward = new ActionPareto(new Qvector(-1, 0), 1);
//
//		try {
//			Point movement = actions.get(action);
//			if (isValidPosition(x + movement.x, y + movement.y)) {
//				x += movement.x;
//				y += movement.y;
//			}
//
//			if (x == 1 && y == 1) {
//				reward.setPareto(new Qvector(-1, first_reward));
//				// resetPosition();
//			}
//
//			if (x == 6 && y == 1) {
//				reward.setPareto(new Qvector(-1, 1000-first_reward));
//				// resetPosition();
//			}
//
//			if (count > 100) {
//				System.out.println("count>100:");
//
////			    printOpenLocationClassifiers(finalStateCount, maze, nxcs,weight);
//
//				resetPosition();
////				action = -1;
//				System.out.println("reset:");
//				reward.setPareto(new Qvector(-1, -1));// TODO:is that
//														// correct??????
//			}
//		} catch (Exception e) {
//			System.out.println(String.format("%s  %d", state, action));
//			throw e;
//		}
//
//		return reward;
//	}
//
//	// public Qvector getReward(String state, int action) {
//	// count++;
//	// Qvector reward = new Qvector(-1, 0);
//	//
//	// Point movement = actions.get(action);
//	// if (isValidPosition(x + movement.x, y + movement.y)) {
//	// x += movement.x;
//	// y += movement.y;
//	// }
//	//
//	// if (x == 1 && y == 1) {
//	// reward.setQvalue(-1, 1);
//	// // resetPosition();
//	// }
//	//
//	// if (x == 1 && y == 6) {
//	// reward.setQvalue(-1, 10);
//	// // resetPosition();
//	// }
//	//
//	// if (count > 100) {
//	// resetPosition();
//	// action=-1;//???
//	// reward.setQvalue(-1, 0);
//	// }
//	//
//	// return reward;
//	// }
//
//	public boolean isEndOfProblem(String state) {
//		for (Point finalState : finalStates) {
//			if (getStringForState(finalState.x, finalState.y).equals(state)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public static void main(String[] args) throws IOException {
//
//		Map<Integer, Map<Integer, Double>> tempList = new HashMap<Integer, Map<Integer, Double>>();
//		BufferedWriter writer = null;
//
//		// create a temporary file
//		String timeLog = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());// yyyyMMdd_HHmmss
//		File logFile = new File(timeLog);
//
//		writer = new BufferedWriter(new FileWriter(logFile));
//
//		int totalCalcTimes = 1;
//		int finalStateUpperBound = 635;
//
//		act.add(0);
//		act.add(1);
//		act.add(2);
//		act.add(3);
//
//		try {
//
//			// distance and exploration setting
//			double[][] weights = new double[][] { { 0, 1 }, { 1, 0 }, { 0.5, 0.5 } };// ,
//																						// "MIN",
//																						// "MAX",
//																						// "CORE"
//																						// };
//
//			String[] actionSelectionMethods = { "e-greedy" };
//
//			NXCSParameters params = new NXCSParameters();
//			// Another set of parameters Woods1, Woods101
//
//			params.N = 3000;
//			params.stateLength = 24;
//			params.numActions = 4;
//			params.rho0 = 1000;
//			params.pHash = 0.1;
//			params.gamma = 0.5;
//			params.crossoverRate = 0.8;
//			params.mutationRate = 0.04;
//			params.thetaMNA = 4;
//			params.thetaGA = 500;
//			// params.thetaGA = 0;
//			// params.e0 = 0.05;
//			params.e0 = 0.05;
//			params.thetaDel = 200;
//			params.doActionSetSubsumption = false;
//			params.doGASubsumption = false;
//			params.weights = new ArrayList<Point>();
//			params.weights.add(new Point(1,1));
//			params.obj1 = new int[]{10,100,200,300,400,500,600,700,800,900,1000};
//
//
//			int finalStateCount = 1;
//			boolean logged = false;
//			int resultInterval = 50;
//			int numOfChartBars = 20;
//			// ArrayList<Point> traceWeights = new ArrayList<Point>();
//			// traceWeights.add(new Point(10, 90));
//			// traceWeights.add(new Point(95, 5));
//
//			// finalStateUpperBound / 20) / 10 * 10 should be 20
//			int chartXInterval = ((finalStateUpperBound / numOfChartBars) > 10)
//					? (finalStateUpperBound / numOfChartBars) / 10 * 10 : 10;
//
//			for (String actionSelectionMethod : actionSelectionMethods) {
//				for (int obj_num=0; obj_num< params.obj1.length;obj_num++) {
//					// params.actionSelection = actionSelectionMethod;
//
//					double first_Freward=params.obj1[obj_num];
//					double second_Freward=1000-params.obj1[obj_num];
//
//
//
//					for (int trailIndex = 0; trailIndex < totalCalcTimes; trailIndex++) {
//						maze1_result maze = new maze1_result("data/maze1.txt");
//						NXCS nxcs = new NXCS(maze, params);
//
//						// Trace trace = new Trace(maze, params);
//
//						// reset trail status
////						 maze.resetToSamePosition(new Point(5, 1));
////						maze.resetPosition();
//						// *****************
//						int i = 1;
//                        maze.resetPosition();
////                        maze.resetToSamePosition(new Point(2, 1));
//
////                        double[] weightSet = weight;
////                        nxcs.runIteration(finalStateCount, maze.getState(), weightSet, i);
//
//						finalStateCount = 1;
//
//						// clear stats
//						// stats.clear();
//						//
//						// StatsLogger logger = new StatsLogger(chartXInterval,
//						// 0);
//						// StepStatsLogger stepLogger = new
//						// StepStatsLogger(chartXInterval, 0);
//
//						System.out.println(
//								String.format("######### begin to run of: Action:%s - first reward:%s - Trail#: %s ",
//										actionSelectionMethod, params.obj1[obj_num], trailIndex));
//
//						// begin
//						// **************************!!!!!!!!
//
//						while (finalStateCount < finalStateUpperBound) {
//							double[] weightSet = weights[0];
//							// weights
//							nxcs.runIteration(finalStateCount, maze.getState(), weightSet, i, params.obj1[obj_num]);
//							i++;
//							System.out.println("next step:" + i);
//
//
//							maze.printOpenLocationClassifiers(finalStateCount, maze, nxcs, weightSet, params.obj1[obj_num]);
//
//
//							if (maze.isEndOfProblem(maze.getState())) {
////								maze.resetPosition();
////								System.out.println("goalstate*************************");
//								maze.resetToSamePosition(new Point(2, 1));
//								finalStateCount++;
//								logged = false;
//								// System.out.println(finalStateCount);
//							}
//
//							// analyst results
//							// if (((finalStateCount % resultInterval ==
//							// 0)||(finalStateCount<100)) && !logged) {
//							if ((finalStateCount % resultInterval == 0) && !logged) {
//								// test algorithem
//								System.out.println("testing process: Trained on " + finalStateCount + " final states");
//
//								int test = 0;
//								int timestamp = 0;
//								System.out.println("strat testing:");
//
//								maze.resetToSamePosition(new Point(2, 1));
//
//								//testing process
////								while (test < 10) {
////									// String state = maze.getState();
////									String state = maze.getState();
////									// System.out.println("get state");
////									int action = nxcs.classify(state, weightSet);
////									// System.out.println("choose action");
////									ActionPareto r = maze.getReward(state, action);
////									if (r.getAction() == 5) {
////										System.out.println("get stucked:classfiers:");
////
////										maze.printOpenLocationClassifiers(finalStateCount, maze, nxcs, weightSet);
////									}
////									// System.out.println("take testing:");
////									if (maze.isEndOfProblem(maze.getState())) {
////										maze.resetToSamePosition(new Point(2, 1));
////										// maze.resetPosition();
////										test++;
////										// System.out.println("test:"+ test);
////									}
////									timestamp++;
////
////								}
//
//								finalStateCount++;
//
//								System.out.println("avg steps:" + ((double) (timestamp)) / test);
//
//								System.out.println("classfiers:");
//
//								//TODO:print reward_l(PA[1]),reward_r(PA[2]),deltaReward and maxReward
//								maze.printOpenLocationClassifiers(finalStateCount, maze, nxcs, weightSet,params.obj1[obj_num]);
//
//							}
//
//							// run function below every 50 steps
//
//							// endof while
//
//							// System.out.println("Trained on " +
//							// finalStateCount + " final states - " +
//							// actionSelectionMethod
//							// + " - " + distCalcMethod);
//
//						} // endof z loop
//
//					} // calculator loop
//				} // action selection loop
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				// Close the writer regardless of what happens...
//				writer.close();
//			} catch (Exception e) {
//			}
//		} // endof try
//	}
//
//	private void printOpenLocationClassifiers(int timestamp, maze1_result maze, NXCS nxcs, double[] weight, double obj_r1) {
//		System.out.println("R1 is:"+obj_r1+" R2 is:"+(1000-obj_r1));
//
//		for (Point p : maze.openLocations) {
//			System.out.println(String.format("%d\t location:%d,%d", timestamp, (int) p.getX(), (int) p.getY()));
//
//
//			List<Classifier> C = nxcs.generateMatchSet(maze.getStringForState(p.x, p.y));
//			double[] PA1 = nxcs.generatePredictions(C, 0);
//			for (int i = 0; i < PA1.length; i++) {
//				System.out.println("PA1[" + i + "]:" + PA1[i]);
//			}
//			double[] PA2 = nxcs.generatePredictions(C, 1);
//			for (int i = 0; i < PA2.length; i++) {
//				System.out.println("PA2[" + i + "]:" + PA2[i]);
//			}
//			double[] PA = nxcs.generateTotalPredictions(C, weight);
//			for (int i = 0; i < PA.length; i++) {
//				System.out.println("PAt[" + i + "]:" + PA[i]);
//			}
//
//			//Q_finalreward
//			double Q_finalreward_left=PA1[1];
//			double Q_finalreward_right=PA1[2];
//			double Q_finalreward_delta=PA1[1]-PA1[2];
//			double Q_finalreward_max=0;
//			if(PA1[1]>PA1[2]){
//				Q_finalreward_max=PA1[1];
//			}else{
//				Q_finalreward_max=PA1[2];
//			}
//
//			//Q_steps
//			double Q_steps_left=PA2[1];
//			double Q_steps_right=PA2[2];
//			double Q_steps_delta=PA2[1]-PA2[2];
//			double Q_steps_max=0;
//			if(PA2[1]>PA2[2]){
//				Q_steps_max=PA2[1];
//			}else{
//				Q_steps_max=PA2[2];
//			}
//
//			//Q_weighted sum value for different weights
//			//TODO:Q_weighted sum value for different weights
//
//
//
//			for (int action : act) {
//
//				List<Classifier> A = C.stream().filter(b -> b.action == action).collect(Collectors.toList());
//				Collections.sort(A, new Comparator<Classifier>() {
//					@Override
//					public int compare(Classifier o1, Classifier o2) {
//						return o1.fitness == o2.fitness ? 0 : (o1.fitness > o2.fitness ? 1 : -1);
//					}
//				});
//
//				System.out.println("action:" + action);
//				// TODO:
//				// 1.why not print fitness of cl???????
//				// 2.print PA for each state to see if PA correct
//				System.out.println(A);
//
//			}
//
//		} // open locations
//	}
//
//	public ArrayList<ArrayList<StepSnapshot>> getOpenLocationExpectPaths() {
//		ArrayList<ArrayList<StepSnapshot>> expect = new ArrayList<ArrayList<StepSnapshot>>();
//		ArrayList<StepSnapshot> e21 = new ArrayList<StepSnapshot>();
//		e21.add(new StepSnapshot(new Point(2, 1), new Point(1, 1), 1));
//		e21.add(new StepSnapshot(new Point(2, 1), new Point(6, 1), 4));
//		expect.add(e21);
//		ArrayList<StepSnapshot> e31 = new ArrayList<StepSnapshot>();
//		e31.add(new StepSnapshot(new Point(3, 1), new Point(1, 1), 2));
//		e31.add(new StepSnapshot(new Point(3, 1), new Point(6, 1), 3));
//		expect.add(e31);
//		ArrayList<StepSnapshot> e41 = new ArrayList<StepSnapshot>();
//		e41.add(new StepSnapshot(new Point(4, 1), new Point(6, 1), 2));
//		expect.add(e41);
//		ArrayList<StepSnapshot> e51 = new ArrayList<StepSnapshot>();
//		e51.add(new StepSnapshot(new Point(5, 1), new Point(6, 1), 1));
//		expect.add(e51);
//
//		return expect;
//	}
//}
