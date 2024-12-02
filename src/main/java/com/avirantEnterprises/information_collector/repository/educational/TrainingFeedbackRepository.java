package com.avirantEnterprises.information_collector.repository.educational;

import com.avirantEnterprises.information_collector.model.educational.TrainingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingFeedbackRepository extends JpaRepository<TrainingFeedback, Long> {
}
