package com.bautistacarpintero.solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverHashMapIndexes extends Solver {

    @Override
    public List<Pair> solve(int[] data, int target) {

        // Se inicializa la lista de pares que va a ser retornada
        List<Pair> pairs = new ArrayList<>();

        // Se crea un mapa con los valores de data[i]
        // y una lista de las posiciones en las que se encuentra
        Map<Integer, List<Integer>> values = new HashMap<>(data.length);


        for (int i = 0; i < data.length; i++) {

            // Es necesario buscar si esta el complemento para llegar al target

            int diff = target - data[i];
            if (values.containsKey(diff)) {
                List<Integer> matchesIndexes = values.get(diff);

                for (int mi : matchesIndexes) {
                    pairs.add(new Pair(data[i], data[mi]));
                }
            }

            // Se agrega el dato nuevo en las listas de indices
            if (values.containsKey(data[i])) {
                List<Integer> indexes = values.get(data[i]);
                indexes.add(i);
            } else {
                List<Integer> indexes = new ArrayList<>();
                indexes.add(i);
                values.put(data[i], indexes);
            }
        }
        return pairs;
    }
}
