# README.md – Informe del Proyecto de Resolución de Laberintos

---

## 📘 Índice
1.  *Carátula*
2.  *Descripción del Problema*
3.  *Propuesta de Solución*
    * 3.1. Marco Teórico
    * 3.2. Tecnologías Utilizadas
    * 3.3. Estructura del Proyecto (MVC + DAO)
    * 3.4. Diagrama UML
    * 3.5. Capturas de Interfaz
    * 3.6. Código de Ejemplo Comentado
4.  *Conclusiones*
5.  *Recomendaciones y Aplicaciones Futuras*

---

## 📌 1. Carátula

![alt text](image.png)

*Universidad Politecnica  Saleciana*

*Materia:* Estructuras de Datos

*Nombre del estudiante:* Daniel Eduardo Uyaguari Guamán, Steve Mateo Brito Guaricela, Juan Andres Tenesaca Criollo, Thalia Maribel Sagbay Peña.

*Correo institucional:* duyaguarig@est.ups.edu.ec, sbritog@est.ups.edu.ec, jtenesacac4@est.ups.edu.ec, tsagbayp@est.ups.edu.ec 

---

## 🧩 2. Descripción del Problema

Imagina la clásica metáfora del *ratón en un laberinto* buscando el queso. Su objetivo es encontrar el destino de la manera más eficiente, pero sin un mapa previo. El desafío central de este proyecto es desarrollar una aplicación que simule este escenario, permitiendo la *visualización interactiva de laberintos* y la aplicación de diversos *algoritmos clásicos de búsqueda* para que el "ratón" (nuestro algoritmo) encuentre la mejor ruta posible.

Este proyecto surge de la necesidad fundamental de *comprender en profundidad y aplicar algoritmos de búsqueda* en un contexto práctico y visual. Permite observar las diferencias de rendimiento, eficiencia y comportamiento de cada algoritmo al enfrentarse a un entorno gráfico dinámico, facilitando así la elección del algoritmo más adecuado para diferentes escenarios de laberintos.

---

## 💡 3. Propuesta de Solución

La solución propuesta es una *aplicación de escritorio interactiva desarrollada en Java Swing* que permite a los usuarios diseñar laberintos personalizados y aplicar diferentes algoritmos de resolución. La aplicación no solo encuentra caminos, sino que también *visualiza el proceso de búsqueda paso a paso* y *registra métricas de rendimiento* (longitud del camino y tiempo de ejecución) para cada algoritmo, permitiendo una comparación objetiva.

### 3.1. Marco Teórico

Para abordar el problema de la resolución de laberintos, se implementaron y compararon los siguientes algoritmos de búsqueda, cada uno con sus propias características y casos de uso óptimos:

* *DFS (Depth-First Search - Búsqueda en Profundidad):*
    * *Descripción:* Este algoritmo explora tan profundo como sea posible a lo largo de cada rama antes de retroceder. Utiliza una pila (implícita en la recursión o explícita) para mantener el rastro de los nodos a visitar.
    * *Características:* Es eficiente en términos de memoria para laberintos profundos. No garantiza el camino más corto. Puede implementarse de forma recursiva.

* *BFS (Breadth-First Search - Búsqueda en Amplitud):*
    * *Descripción:* Explora el laberinto por "niveles" o "capas", visitando primero todos los vecinos de un nodo antes de pasar a los vecinos de esos vecinos. Utiliza una cola para gestionar los nodos a visitar.
    * *Características:* Garantiza encontrar el camino más corto en laberintos no ponderados (como el nuestro). Requiere más memoria que DFS para laberintos amplios.

* *Backtracking (Retroceso):*
    * *Descripción:* Una técnica algorítmica general para encontrar soluciones a problemas que involucran la construcción de una solución paso a paso. Cuando una solución parcial no lleva a una solución completa (ej. un callejón sin salida en el laberinto), el algoritmo "retrocede" para probar otra alternativa. A menudo se implementa recursivamente.
    * *Características:* Es una base para algoritmos como DFS. Permite explorar todas las posibles rutas hasta encontrar una solución o determinar que no existe ninguna. Se implementa en variantes recursivas como "Recursivo Completo BT".

* *Recursión:*
    * *Descripción:* Es una técnica de programación donde una función se llama a sí misma para resolver subproblemas más pequeños del mismo tipo. En este proyecto, la recursión se utiliza como una herramienta fundamental para la implementación de DFS y de las diferentes variantes del algoritmo recursivo.
    * *Características:* Permite soluciones elegantes y concisas para problemas que se pueden descomponer en problemas idénticos más pequeños. Puede llevar a problemas de desbordamiento de pila si la profundidad de la recursión es muy grande.

*Variantes Específicas Implementadas:*
Se han desarrollado versiones específicas de los algoritmos para ofrecer una comparación más detallada:
* *Recursivo (2 direcciones):* Una implementación recursiva básica, posiblemente explorando en un subconjunto de direcciones o con limitaciones.
* *Recursivo (4 direcciones):* Una implementación recursiva que explora todas las direcciones cardinales (arriba, abajo, izquierda, derecha).
* *Recursivo (4 direcciones + Backtracking):* Una versión recursiva que, además de explorar en 4 direcciones, incorpora el concepto de "backtracking" para desmarcar celdas del camino explorado si no conducen a la solución.

Cada uno de estos algoritmos presenta ventajas y limitaciones significativas dependiendo de la estructura del laberinto y del objetivo deseado (por ejemplo, encontrar el camino más corto o simplemente cualquier camino). Esta aplicación permite observar estas diferencias de manera tangible.

### 3.2. Tecnologías Utilizadas

El proyecto fue desarrollado utilizando las siguientes tecnologías:

* *Lenguaje de Programación:* *Java*
* *Interfaz Gráfica (GUI):* *Java Swing*, la biblioteca estándar de Java para construir interfaces de usuario de escritorio. Permite la creación de componentes visuales interactivos como botones, menús y paneles personalizados (MazePanel).
* *Gestión de Datos/Persistencia:* Los resultados de la ejecución de los algoritmos (nombre, longitud del camino, tiempo de ejecución) se persisten en un **archivo CSV (results.csv).
* *Organización de Clases y Paquetes:* Se aplicó el *Patrón de Diseño DAO (Data Access Object)* para abstraer y separar la lógica de persistencia de datos del resto de la aplicación, facilitando la gestión y el mantenimiento de los resultados de los algoritmos.
* *Gráficos Adicionales:* La visualización de los tiempos de ejecución por algoritmo se realiza utilizando la librería *JFreeChart*, una potente herramienta para la creación de gráficos en Java.

### 3.3. Estructura del Proyecto (MVC + DAO)

El proyecto sigue una arquitectura *Modelo-Vista-Controlador (MVC), complementada con el patrón **DAO* para la capa de persistencia. Esta estructura promueve la separación de responsabilidades, mejorando la modularidad, mantenibilidad y escalabilidad del código.
```
src/
├── MazeApp.java                # Punto de entrada principal de la aplicación.
├── controllers/                # Contiene la lógica de negocio y coordina la 
interacción entre la vista y el modelo.
│   └── MazeController.java     # Maneja eventos de la UI, inicia algoritmos y actualiza la vista.
├── dao/                        # Define interfaces para la capa de acceso a datos.
│   ├── AlgorithmResultDAO.java # Interfaz para operaciones CRUD de resultados de algoritmos.
│   └── daoImpl/                # Implementaciones concretas de las interfaces DAO.
│       └── AlgorithmResultDAOFile.java # Implementación que guarda/carga resultados en un archivo CSV.
├── models/                     # Contiene las clases de datos y la lógica de dominio.
│   ├── AlgorithmResult.java    # Representa el resultado de un algoritmo (algoritmo, camino, tiempo).
│   ├── Cell.java               # Representa una celda individual en el laberinto.
│   ├── CellState.java          # Enumeración para definir el estado de una celda (muro, inicio, fin, etc.).
│   └── SolveResults.java       # (Posiblemente para agrupar múltiples resultados o estadísticas generales).
├── resources/                  # Directorio reservado para futuros recursos como imágenes, configuraciones, etc.
├── solver/                     # Define la interfaz para los algoritmos de resolución de laberintos.
│   ├── MazeSolver.java         # Interfaz genérica para cualquier algoritmo de resolución.
│   └── solverImpl/             # Implementaciones específicas de los algoritmos de resolución.
│       ├── MazeSolverBFS.java          # Implementación del algoritmo de Búsqueda en Amplitud.
│       ├── MazeSolverDFS.java          # Implementación del algoritmo de Búsqueda en Profundidad.
│       ├── MazeSolverRecursivo.java    # Algoritmo recursivo básico.
│       ├── MazeSolverRecursivoCompleto.java # Algoritmo recursivo con exploración completa.
│       └── MazeSolverRecursivoCompletoBT.java # Algoritmo recursivo completo con backtracking.
└── views/                      # Contiene las clases responsables de la interfaz de usuario.
├── MazeFrame.java          # La ventana principal de la aplicación del laberinto.
├── MazePanel.java          # El panel donde se dibuja y se interactúa con el laberinto.
└── ResultadosDialog.java   # Diálogo para mostrar la tabla y gráfica de resultados.

```
### 3.4. Diagrama UML

![alt text](image-1.png)
El *Diagrama UML* adjunto ilustra la *arquitectura Modelo-Vista-Controlador (MVC)* implementada en el proyecto, junto con el patrón *DAO (Data Access Object)*. Se puede observar claramente cómo las clases se relacionan:

* **MazeApp** actúa como el punto de entrada, inicializando la aplicación.
* **MazeFrame** y **MazePanel** representan la *Vista*, encargándose de la presentación visual y la interacción del usuario.
* **MazeController** es el *Controlador*, que recibe las acciones del usuario desde la Vista y coordina la lógica de negocio, interactuando con los modelos y los algoritmos de resolución.
* Las clases en el paquete **models** (Cell, CellState, AlgorithmResult, SolveResults) constituyen el *Modelo*, encapsulando la estructura de datos y el estado del laberinto y los resultados.
* El paquete **solver** define la interfaz **MazeSolver** y sus implementaciones concretas en **solverImpl*, mostrando la aplicación del **patrón Strategy* para los algoritmos de búsqueda.
* La capa *DAO* (AlgorithmResultDAO y AlgorithmResultDAOFile) se encarga de la *persistencia de los resultados*, desacoplando la lógica de negocio de los detalles de almacenamiento (en este caso, un archivo CSV).

Esta estructura garantiza una clara separación de responsabilidades y facilita el mantenimiento y la extensión del proyecto.

### 3.5. Capturas de Interfaz

*[Aquí debes agregar al menos 2 capturas en diferentes laberintos, mostrando el uso de un algoritmo. Ejemplo:]*

* *Laberinto con solución BFS:*

    Esta captura muestra un laberinto con un punto de inicio (verde), un punto final (rojo) y varios muros (negro). Se ha aplicado el algoritmo *BFS*, y el camino más corto encontrado está resaltado en azul.

* *Laberinto con solución DFS (modo paso a paso):*

    Aquí se observa el mismo laberinto, pero se ha utilizado el algoritmo *DFS* en modo "paso a paso". Las celdas exploradas que forman parte de la ruta actual están marcadas, mostrando cómo DFS profundiza antes de retroceder.

### 3.6. Código de Ejemplo Comentado

A continuación, se presenta un fragmento de código comentado del algoritmo *DFS (Depth-First Search)*, tal como se implementó en el proyecto. Este método recursivo ilustra la lógica central de la búsqueda en profundidad.

java
package solver.solverImpl;

import java.util.*;
import models.Cell;
import models.CellState;
import solver.MazeSolver;

public class MazeSolverDFS implements MazeSolver {
    @Override
    public String getName() {
        return "DFS (Depth-First Search)";
    }

    @Override
    public List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol) {
        int rows = maze.length;
        int cols = maze[0].length;

        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols ||
                endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
            System.err.println("Error: Coordenadas de inicio o fin fuera de los límites del laberinto.");
            return Collections.emptyList();
        }

        if (maze[startRow][startCol].getState() == CellState.WALL ||
                maze[endRow][endCol].getState() == CellState.WALL) {
            System.err.println("Error: La celda de inicio o fin es una pared.");
            return Collections.emptyList();
        }

        if (startRow == endRow && startCol == endCol) {
            List<Cell> path = new ArrayList<>();
            path.add(maze[startRow][startCol]);
            return path;
        }

        boolean[][] visited = new boolean[rows][cols];
        Cell[][] prev = new Cell[rows][cols];
        Stack<Cell> stack = new Stack<>();

        Cell startCell = maze[startRow][startCol];
        stack.push(startCell);
        visited[startRow][startCol] = true;

        boolean foundPath = false;

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int r = current.getRow();
            int c = current.getCol();

            if (r == endRow && c == endCol) {
                foundPath = true;
                break;
            }

            for (Cell neighbor : getNeighbors(maze, current)) {
                int nr = neighbor.getRow();
                int nc = neighbor.getCol();
                if (!visited[nr][nc] && neighbor.getState() != CellState.WALL) {
                    stack.push(neighbor);
                    visited[nr][nc] = true;
                    prev[nr][nc] = current;
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        if (foundPath) {
            Cell curr = maze[endRow][endCol];
            while (curr != null && !(curr.getRow() == startRow && curr.getCol() == startCol)) {
                path.add(curr);
                curr = prev[curr.getRow()][curr.getCol()];
            }
            if (curr != null) {
                path.add(curr);
            }
            Collections.reverse(path);
        }
        return path;
    }

    private List<Cell> getNeighbors(Cell[][] maze, Cell cell) {
        int rows = maze.length;
        int cols = maze[0].length;
        int r = cell.getRow();
        int c = cell.getCol();
        List<Cell> neighbors = new ArrayList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                neighbors.add(maze[nr][nc]);
            }
        }
        return neighbors;
    }
}

Este algoritmo recursivo de DFS es fundamental para la búsqueda en profundidad en el laberinto. Su simplicidad y la forma en que explora a fondo antes de retroceder lo hacen rápido para encontrar cualquier camino, aunque no necesariamente el más corto.

---

##  4. Conclusiones

- Daniel Uyaguari: 
Sinceramente, fue complicado en un principio, pero una vez que entendí la lógica de cada algoritmo, todo fluyó mejor. El que más me gustó fue DFS, porque siento que se parece mucho a cómo pensamos: nos metemos en un camino hasta donde podamos, y si no sirve, retrocedemos. Tiene mucho sentido a la hora de resolver un laberinto. En cuanto al proyecto, me encantó ver cómo todo se integra: la lógica, la interfaz, los resultados. Aprendí mucho y me deja con ganas de seguir creando cosas más complejas.

- Steve Brito:
Me resulto bastante interesante realizar el codigo que permita ver cómo un el ratón encuentra su camino. Me quedo con BFS como el más óptimo, por su eficiencia y la forma en que asegura siempre el camino más corto. Me pareció directo, confiable. Además, aplicar estos algoritmos me ayudó a ver que lo que antes eran solo líneas de código que no comprendia del todo, ahora tienen vida cuando los visualizas.

- Thalia Sagbay:
Lo que más me gustó fue entender cómo funcionan estos algoritmos detrás de escena. Personalmente, lo que más me gusto fue el Backtracking, porque representa esa idea de intentar, equivocarse, y volver a intentar. Aunque no sea el más eficiente. Este proyecto no solo me enseñó sobre estructuras de datos, sino también sobre paciencia y perseverancia.

- Andres Tenesaca:
Este proyecto fue un desafío interesante. Si tengo que elegir un algoritmo por rendimiento puro, diría BFS sin pensarlo mucho. Va al grano, encuentra lo que necesita y se nota su eficiencia. Pero también entendí que cada algoritmo tiene su encanto. Me siento satisfecho por haber aportado al proyecto y porque ahora veo con más claridad cómo aplicar esto en problemas reales, incluso en áreas como inteligencia artificial.


En síntesis, esta práctica nos permitió comprender que *cada algoritmo tiene su "momento ideal"*. La elección del algoritmo más adecuado depende críticamente de los requisitos específicos del problema: ¿se necesita el camino más corto, la primera solución encontrada, o se busca una exploración completa? La visualización interactiva fue fundamental para internalizar estos conceptos de manera tangible y lógica.

---

## 💬 5. Recomendaciones y Aplicaciones Futuras

Este proyecto sienta una base sólida para futuras expansiones y mejoras. Aquí algunas recomendaciones y posibles aplicaciones:

### 5.1. Mejoras en la Implementación

* *Implementación de A\ (A-Star):** Sería de gran valor añadir el algoritmo A\*. Este algoritmo heurístico es conocido por su eficiencia en la búsqueda del camino más corto, utilizando información adicional (heurística) para guiar la búsqueda y reducir el espacio de estados explorado.
* *Optimización con Programación Dinámica:* Para ciertas variantes recursivas o problemas relacionados, se podría incorporar la *memoización* (caching de resultados de subproblemas) o la *tabulación* para reducir la complejidad temporal y evitar recálculos redundantes.
* *Mejora de la Visualización:*
    * *Animación más Fluida:* Optimizar la velocidad de la animación en el modo "paso a paso" y permitir al usuario controlar la velocidad.
    * *Feedback Visual Mejorado:* Mostrar el orden de exploración de las celdas (por ejemplo, celdas "abiertas" y "cerradas" en BFS/DFS) para una comprensión más profunda del algoritmo.
    * *Resaltado del Camino Óptimo:* Una vez finalizado el paso a paso o la resolución directa, resaltar de forma distintiva el camino final encontrado.
* *Estadísticas Avanzadas:*
    * Además del tiempo de ejecución y la longitud del camino, se podrían registrar y mostrar otras métricas como el número de celdas visitadas, el número de operaciones de memoria, o el uso de la pila/cola.
    * Permitir la exportación de resultados a otros formatos (ej. JSON) para análisis externos.
* *Generación de Laberintos:* Implementar algoritmos para generar laberintos automáticamente (ej. algoritmo de Prim o Kruskal para laberintos perfectos, o DFS para laberintos más complejos), lo que añadiría otra capa de interactividad al proyecto.

### 5.2. Aplicaciones Futuras

Los principios y algoritmos explorados en este proyecto tienen una amplia gama de aplicaciones en diversos campos:

* *Juegos:*
    * *IA de Personajes:* Creación de IA para personajes no jugables (NPCs) en videojuegos, permitiéndoles encontrar rutas en entornos complejos.
    * *Generación Procedural:* Generación de mapas o niveles en juegos.
* *Robótica:*
    * *Planificación de Rutas:* Robots móviles navegando en entornos desconocidos o con obstáculos, como robots de almacén o vehículos autónomos.
    * *Logística:* Optimización de rutas para vehículos de reparto o drones.
* *Inteligencia Artificial:*
    * *Agentes de Búsqueda:* Fundamento para el desarrollo de agentes inteligentes que resuelven problemas mediante la búsqueda en espacios de estados.
    * *Sistemas Expertos:* En problemas de diagnóstico o toma de decisiones donde se requiere explorar un árbol de posibilidades.
* *Desarrollo de Software:*
    * *Optimización de Redes:* Enrutamiento de paquetes en redes de computadoras.
    * *Análisis de Grafos:* Resolución de problemas en estructuras de datos tipo grafo.
    * *Compiladores:* Análisis de flujo de control o búsqueda de dependencias.
* *Simulación y Modelado:*
    * Simulación de flujos de tráfico, propagación de enfermedades o cualquier sistema donde se requiera encontrar caminos o secuencias de eventos.

Este proyecto ha sido una excelente base para entender la potencia de los algoritmos de búsqueda y cómo su correcta aplicación puede resolver problemas complejos en diversos dominios.