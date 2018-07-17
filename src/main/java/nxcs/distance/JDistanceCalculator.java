package nxcs.distance;

import nxcs.Qvector;

import java.util.ArrayList;

public class JDistanceCalculator extends DistanceCalculator{

	@Override
	public double getDistance(ArrayList<Qvector> qSet1, ArrayList<Qvector> qSet2) {
		
		int uniSize = getUnionSet(qSet1, qSet2).size();
		int interSize = getIntersectionSet(qSet1, qSet2).size();

		return Math.abs(uniSize-interSize)/(double)uniSize;
	}

}
