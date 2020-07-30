package com.bautistacarpintero.solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverHashMapIndexes2 extends Solver {

    @Override
    public List<Pair> solve(int[] data, int target) {


        List<Pair> pairs = new ArrayList<>();
        Map<Integer, List<Integer>> values = new HashMap<>(data.length);

        for (int i = 0; i < data.length; i++) {


            int diff = target - data[i];

            // La lista matchesIndexes ahora se consulta fuera del if
            // y se cambia la condicion del if para ahorar el llamado al containsKey

            List<Integer> matchesIndexes = values.get(diff);
            if (matchesIndexes != null) {

                // Este for-each se cambio por un for tradicional con indices
                // ya que de esta forma no se crean iteradores y se ahorran
                // llamados a metodos

                for (int j = 0; j < matchesIndexes.size(); j++) {

                    /*
                        Ahora que matchesIndexes se itera usando un for tradicional es necesario recuperar el valor de
                        los indices de los matches (mi) usando matchesIndexes.get(j) de esa forma el codigo quedaria:

                            int mi = matchesIndexes.get(j);
                            pairs.add(new Pair(data[i], data[mi]));

                        Como ya sabemos que data[mi] contiene el valor de la diferencia entre data[i] y el target
                        podria evitarse la variable 'mi' y un llamado a metodo de forma directa la diferencia, la cual
                        esta disponible en la var diff.

                     */
                    pairs.add(new Pair(data[i], diff));
                }
            }

            List<Integer> indexes = values.get(data[i]);
            if (indexes != null) {
                indexes.add(i);
            } else {
                indexes = new ArrayList<>();
                indexes.add(i);
                values.put(data[i], indexes);
            }
        }
        return pairs;
    }
}
