// Interfaces de acceso a datos
package dao;

import models.AlgorithmResult;
import java.util.List;

public interface AlgorithmResultDAO {
    void saveResult(AlgorithmResult result);
    List<AlgorithmResult> getAllResults();
    void clearResults();
}