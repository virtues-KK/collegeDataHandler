package collegedata.demo.repository;

import collegedata.demo.entity.EnrollMajorScoreLine;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EnrollMajorScoreLineRepo extends JpaRepository<EnrollMajorScoreLine, Long> {
}

