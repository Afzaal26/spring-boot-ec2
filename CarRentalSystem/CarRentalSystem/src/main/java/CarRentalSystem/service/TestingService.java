package CarRentalSystem.service;

import CarRentalSystem.entity.TestingTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestingService {
    TestingTable savePost(TestingTable post);

    List<TestingTable> getAllPosts();

    TestingTable getPostById(Long id);
}
