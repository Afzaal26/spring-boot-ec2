package CarRentalSystem.service.serviceImpl;

import CarRentalSystem.entity.TestingTable;
import CarRentalSystem.repository.TestingRepository;
import CarRentalSystem.service.TestingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final TestingRepository testingRepository;

    public TestingServiceImpl(TestingRepository testingRepository) {
        this.testingRepository = testingRepository;
    }

    @Override
    public TestingTable savePost(TestingTable post) {
        return testingRepository.save(post);
    }

    @Override
    public List<TestingTable> getAllPosts() {
        return testingRepository.findAll();
    }

    @Override
    public TestingTable getPostById(Long id) {
        return testingRepository.findById(id).orElse(null);
    }
}
