package nxcs.distance;

import nxcs.Qvector;

import java.util.ArrayList;

public interface IDistanceCalculator {
	public double getDistance(ArrayList<Qvector> qSet1, ArrayList<Qvector> qSet2);
}
