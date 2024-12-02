package com.avirantEnterprises.information_collector.repository.educational;

import com.avirantEnterprises.information_collector.model.educational.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
}
