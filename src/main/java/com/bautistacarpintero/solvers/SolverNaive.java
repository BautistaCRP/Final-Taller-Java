package com.bautistacarpintero.solvers;

import java.util.ArrayList;
import java.util.List;



public class SolverNaive extends Solver {


	public List<Pair> solve(int[] data, int target) {



		List<Pair> pairs = new ArrayList<>();
		
        for (int i = 0; i < data.length; i++) 
            for (int j = i + 1; j < data.length; j++) 
                if ((data[i] + data[j]) == target)
                    pairs.add(new Pair(data[i],data[j]));

		return pairs;
	}

}
