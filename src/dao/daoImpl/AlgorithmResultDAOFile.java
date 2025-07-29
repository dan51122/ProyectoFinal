
package dao.daoImpl;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import java.io.*;
import java.util.*;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {
    private static final String FILE_PATH = "results.csv";

    @Override
    public void saveResult(AlgorithmResult result) {
        List<AlgorithmResult> results = getAllResults();
        boolean updated = false;
        for (int i = 0; i < results.size(); i++) {
            AlgorithmResult r = results.get(i);
            if (r.getAlgorithmName().equals(result.getAlgorithmName()) &&
                r.getPathLength() == result.getPathLength()) {
                results.set(i, result);
                updated = true;
                break;
            }
        }
        if (!updated) {
            results.add(result);
        }
        writeAllResults(results);
    }

    @Override
    public List<AlgorithmResult> getAllResults() {
        List<AlgorithmResult> results = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return results;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        String name = parts[0];
                        int length = Integer.parseInt(parts[1]);
                        long time = Long.parseLong(parts[2]);
                        results.add(new AlgorithmResult(name, length, time));
                    } catch (NumberFormatException ex) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    private void writeAllResults(List<AlgorithmResult> results) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (AlgorithmResult r : results) {
                pw.println(r.getAlgorithmName() + "," + r.getPathLength() + "," + r.getExecutionTime());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearResults() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}