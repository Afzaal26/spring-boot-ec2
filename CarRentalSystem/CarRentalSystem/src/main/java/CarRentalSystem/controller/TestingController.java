package CarRentalSystem.controller;

import CarRentalSystem.entity.TestingTable;
import CarRentalSystem.service.TestingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TestingController {
    private final TestingService testingService;

    public TestingController(TestingService testingService) {
        this.testingService = testingService;
    }

    @PostMapping("/post")
    public ResponseEntity<TestingTable> createPost(@RequestBody TestingTable testingTable) {
        TestingTable savedPost = testingService.savePost(testingTable);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TestingTable>> getAllPosts() {
        List<TestingTable> posts = testingService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TestingTable> getPostById(@PathVariable Long id) {
        TestingTable post = testingService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        }
        return ResponseEntity.notFound().build();
    }
}
