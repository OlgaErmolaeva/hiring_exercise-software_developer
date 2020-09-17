package io.excercise.rss.dao;

import io.excercise.rss.model.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long> {
    AnalysisResult getByRequestId(String requestId);
}
