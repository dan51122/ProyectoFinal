package models;

public class AlgorithmResult {
    private String algorithmName;
    private int pathLength;
    private long executionTime;

    public AlgorithmResult(String algorithmName, int pathLength, long executionTime) {
        this.algorithmName = algorithmName;
        this.pathLength = pathLength;
        this.executionTime = executionTime;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getPathLength() {
        return pathLength;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "Algoritmo: " + algorithmName +
               "\nLongitud del camino: " + pathLength +
               "\nTiempo de ejecución: " + executionTime + " ms";
    }
}