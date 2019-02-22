package collegedata.demo.repository;

import collegedata.demo.entity.EnrollStudentPlan;
import collegedata.demo.entity.ScienceAndArt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollStudentPlanRepo extends JpaRepository<EnrollStudentPlan,Long> {
//    Optional<EnrollStudentPlan> findByProvince_NameAndBatchAndNameAndScienceArtAndCollege_Name(String provinceName, String Batch, String name, ScienceAndArt scienceAndArt,String CollegeName);
}
