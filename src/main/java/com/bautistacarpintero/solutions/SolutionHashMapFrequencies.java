package com.bautistacarpintero.solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionHashMapFrequencies implements IProblemSolver {

    private long lastTime = 0;

    @Override
    public List<Pair> isSumIn(int[] data, int target) {

        long start = System.currentTimeMillis();
        List<Pair> pairs = new ArrayList<>();

        // Dado que no necesito la informacion acerca de los indices de donde provienen
        // los pares, no es necesario guardar la lista de indices en el hashMap, con la
        // frecuencia de los valores es suficiente.

        Map<Integer, Integer> frequencies = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            Integer freq = frequencies.get(data[i]);

            if(freq == null)
                frequencies.put(data[i],1);
            else
                frequencies.put(data[i],freq+1);

            int diff = target - data[i];

            Integer diffFreq = frequencies.get(diff);
            if (diffFreq != null) {

                // Dado que voy a insertar en la solucion el par varias veces acorde a la frecuencia
                //  de la diferencia y sabiendo que siempre es el mismo par, no es necesario crear
                //  nuevas instancias del mismo.

                Pair pair = new Pair(data[i], diff);
                while (diffFreq > 0){
                    pairs.add(pair);
                    diffFreq--;
                }

            }
        }

        this.lastTime = System.currentTimeMillis() - start;
        return pairs;
    }

    @Override
    public long getLastTime() {
        return this.lastTime;
    }
}
