package com.bautistacarpintero.solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionHashMapIndexes implements IProblemSolver {

    private long lastTime = 0;

    public long getLastTime() {
        return lastTime;
    }

    @Override
    public List<Pair> isSumIn(int[] data, int target) {

        long start = System.currentTimeMillis();

        // Se inicializa la lista de pares que va a ser retornada
        List<Pair> pairs = new ArrayList<>();

        // Se crea un mapa con los valores de data[i]
        // y una lista de las posiciones en las que se encuntra
        Map<Integer, List<Integer>> values = new HashMap<>(data.length);


        for (int i = 0; i < data.length; i++) {


            // Se agrega el dato nuevo en las listas de indices
            List<Integer> indexes = values.get(data[i]);

            if (indexes != null) {
                indexes.add(i);
            } else {
                indexes = new ArrayList<>();
                indexes.add(i);
                values.put(data[i], indexes);
            }


            // Es necesario buscar si esta el complemento para llegar al target

            int diff = target - data[i];
            if (values.containsKey(diff)) {
                List<Integer> matchesIndexes = values.get(diff);

                for (int mi : matchesIndexes) {
                    pairs.add(new Pair(data[i],data[mi]));
                }
            }
        }
        this.lastTime = System.currentTimeMillis() - start;
        return pairs;
    }
}
