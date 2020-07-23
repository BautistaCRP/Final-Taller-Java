# Trabajo Final de la catedra Taller de Programación en Java
### Contando pares

Dado un arreglo de enteros y un número target, encontrar los
pares de enteros en el arreglo, cuya suma es igual a target.

| FAQ |  |
| :------------- | :------------- |
| Hay alguna restricción respecto al rango de los números en el arreglo? | No, pueden ser valores arbitrarios. |
| Todos números positivos? | No, puede haber positivos, negativos y cero. | 
| Hay alguna relación entre el valor de target y los valores en el arreglo? | No, target es un valor arbitrario. |
| Pueden considerarse pares de unelemento consigo mismo? | No, el par debe contener elementos en distintas posiciones del arreglo.|
| Puede haber duplicados? | Si. |

#### Ejemplos
``` java
arr[] = {1, 5, 7, -1}
target = 6
// Solución: [(1,5), (7,-1)]

arr[] = {1, 5, 7, -1, 5}
target = 6
// Solución: [(1, 5), (7, -1), (1, 5)]

arr[] = {1, 1, 1, 1}
target = 2
// Solución: [(1,1), (1,1), (1,1), (1,1), (1,1), (1,1)]

arr[] = {10, 12, 10, 15, -1, 7, 6, 5, 4, 2, 1, 1, 1}
target = 11
// Solución: [(10,1), (10,1), (10,1), (12,-1), (10,1), (10,1), (10,1), (7,4), (6,5)]

```
