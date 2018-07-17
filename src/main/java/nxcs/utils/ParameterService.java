package nxcs.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ParameterService {


    /**
     * Loads and parses the parameter file.
     *
     * @param fileName
     */
    private void parseParamsFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                setParam(scan.next(), scan.next());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read parameter file.");
            e.printStackTrace();
        }
    }


    /**
     * Sets the next parameter from the file, checking against a list
     * of possibilities.
     *
     * @param token
     */
    private void setParam(String token, String param) {
        try {
            switch (token) {
//                case "seed":
//                    seed = Long.valueOf(param);
//                    break;
//                case "generations":
//                    generations = Integer.valueOf(param);
//                    break;
//                case "popSize":
//                    popSize = Integer.valueOf(param);
//                    break;
//                case "numObjectives":
//                    numObjectives = Integer.valueOf(param);
//                    break;
//                case "numNeighbours":
//                    numNeighbours = Integer.valueOf(param);
//                    break;
//                case "crossoverProbability":
//                    crossoverProbability = Double.valueOf(param);
//                    break;
//                case "mutationProbability":
//                    mutationProbability = Double.valueOf(param);
//                    break;
//                case "localSearchProbability":
//                    localSearchProbability = Double.valueOf(param);
//                    break;
//                case "stopCrit":
//                    stopCrit = (StoppingCriteria) Class.forName(param).getConstructor(Integer.TYPE).newInstance(generations);
//                    break;
//                case "indType":
//                    indType = (Individual) Class.forName(param).newInstance();
//                    break;
//                case "mutOperator":
//                    mutOperator = (MutationOperator) Class.forName(param).newInstance();
//                    break;
//                case "crossOperator":
//                    crossOperator = (CrossoverOperator) Class.forName(param).newInstance();
//                    break;
//                case "localOperator":
//                    localOperator = (LocalSearchOperator) Class.forName(param).newInstance();
//                    break;
//                case "numLocalSearchTries":
//                    numLocalSearchTries = Integer.valueOf(param);
//                    break;
//                case "outFileName":
//                    outFileName = param;
//                    break;
//                case "frontFileName":
//                    frontFileName = param;
//                    break;
//                case "serviceRepository":
//                    serviceRepository = param;
//                    break;
//                case "serviceTaxonomy":
//                    serviceTaxonomy = param;
//                    break;
//                case "serviceTask":
//                    serviceTask = param;
//                    break;
//                case "tchebycheff":
//                    tchebycheff = Boolean.valueOf(param);
//                    break;
//                case "dynamicNormalisation":
//                    dynamicNormalisation = Boolean.valueOf(param);
//                    break;
//                case "w1":
//                    w1 = Double.valueOf(param);
//                    break;
//                case "w2":
//                    w2 = Double.valueOf(param);
//                    break;
//                case "w3":
//                    w3 = Double.valueOf(param);
//                    break;
//                case "w4":
//                    w4 = Double.valueOf(param);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Invalid parameter: " + token);
            }
        } catch (Exception e) {
            System.err.println("Cannot parse parameter correctly: " + token);
            e.printStackTrace();
        }
    }

}
