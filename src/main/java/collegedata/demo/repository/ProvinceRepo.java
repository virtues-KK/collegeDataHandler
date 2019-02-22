package collegedata.demo.repository;

import collegedata.demo.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProvinceRepo extends JpaRepository<Province, Long> {
    Province findByName(String provinceName);

}

