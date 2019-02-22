package collegedata.demo.repository;

import collegedata.demo.entity.CollegeScoreLine;
import collegedata.demo.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CollegeScoreLineRepo extends JpaRepository<CollegeScoreLine, Long> {
//
//    @Modifying
//    @Query("update college_score_line set scoreLineDiff =  ")
//    public void updateDiff(String collegeName, Integer year,Long enrollbatchId ,String provinceName,String course,Integer scoreLineDiff);
}

