

Por cada uno de los gráficos se genero un ejecutable jar.

Esos ejecutables reciben la ruta del archivo csv que corresponda como argumento:

    java -jar TimesChart.jar timesBenchmark.csv
    java -jar TimesChart-LogScale.jar timesBenchmark.csv
    java -jar MemoryChart.jar memBenchmark.csv

En caso de no encontrarlo en esa ruta lo busca en la carpeta local.
Con clonar el repositorio, y abrir con doble click los jars
contenidos en la carpeta ‘charts’ deberían funcionar correctamente.