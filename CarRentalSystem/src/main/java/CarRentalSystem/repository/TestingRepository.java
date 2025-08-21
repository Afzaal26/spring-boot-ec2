package CarRentalSystem.repository;

import CarRentalSystem.entity.TestingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestingRepository extends JpaRepository<TestingTable, Long> {
}
