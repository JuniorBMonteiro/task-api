package br.com.bmont.task.repository;

import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;
import br.com.bmont.task.util.TaskCreator;
import br.com.bmont.task.util.UserCreator;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    private void init(){
       this.user = userRepository.save(UserCreator.createUserWithoutId());
    }

    @Test
    void findAll_ReturnsPageOfTask_WhenSuccessful(){
        Task taskSaved = taskRepository.save(TaskCreator.createTaskWithoutId(user));
        Pageable pageable = PageRequest.of(0,10);
        Page<Task> tasks = taskRepository.findAll(pageable);
        Task firstTask = tasks.toList().get(0);
        Assertions.assertNotNull(tasks);
        Assertions.assertNotNull(firstTask);
        Assertions.assertEquals(taskSaved, firstTask);
    }

    @Test
    void findById_ReturnsTask_WhenSuccessful(){
        Task taskSaved = taskRepository.save(TaskCreator.createTaskWithoutId(user));
        Task taskFound = taskRepository.findById(taskSaved.getId()).get();
        Assertions.assertNotNull(taskFound);
        Assertions.assertNotNull(taskFound.getId());
        Assertions.assertEquals(taskFound, taskSaved);
    }

    @Test
    void saveTask_ReturnsTask_WhenSuccessful(){
        Task task = TaskCreator.createTaskWithoutId(user);
        Task taskSaved = taskRepository.save(task);
        Assertions.assertNotNull(taskSaved);
        Assertions.assertNotNull(taskSaved.getId());
        Assertions.assertEquals(task.getTask(), taskSaved.getTask());
    }

    @Test
    void deleteTask_RemovesTask_WhenSuccessful(){
        Task taskSaved = taskRepository.save(TaskCreator.createTaskWithoutId(user));
        taskRepository.delete(taskSaved);
        Optional<Task> task = taskRepository.findById(taskSaved.getId());
        Assertions.assertTrue(task.isEmpty());
    }


}