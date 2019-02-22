package collegedata.demo.repository;

import collegedata.demo.entity.SourceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceDataRepo extends JpaRepository<SourceData,Long> {
}
