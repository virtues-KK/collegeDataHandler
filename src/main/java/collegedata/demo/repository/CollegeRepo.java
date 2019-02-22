package collegedata.demo.repository;

import collegedata.demo.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface CollegeRepo extends JpaRepository<College, Long>,JpaSpecificationExecutor <College> {

    Optional<College> findByName(String name);

}

