package nxcs.distance;

public  class DistanceCalculatorUtil {

    public static double calculate(double[] p1, double[] p2) {
        double distance = 0;
        for (int i = 0; i < p1.length; i++) {
            distance = +Math.pow(p1[i] - p2[i], 2);
        }
        return distance;
    }

}
