package collegedata.demo.repository;

import collegedata.demo.entity.CompareData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompareDataRepo extends JpaRepository<CompareData,Long> {
}
