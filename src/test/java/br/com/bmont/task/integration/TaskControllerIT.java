package br.com.bmont.task.integration;

import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;
import br.com.bmont.task.repository.TaskRepository;
import br.com.bmont.task.repository.UserRepository;
import br.com.bmont.task.request.LoginRequest;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import br.com.bmont.task.response.TokenResponse;
import br.com.bmont.task.util.TaskCreator;
import br.com.bmont.task.util.UserCreator;
import br.com.bmont.task.wrapper.PageableResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskControllerIT {
    private final String PATH = "/tasks";
    private String token;
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void init() throws JsonProcessingException {
        this.user = userRepository.save(UserCreator.createUserEncode());
        this.token = getToken();
    }

    String getToken() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest = UserCreator.createLoginRequest();
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(loginRequest), headers);
        ResponseEntity<TokenResponse> response = testRestTemplate
                .postForEntity("/auth", entity, TokenResponse.class);
        return response.getBody().getToken();
    }

    HttpEntity<String> createEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    HttpEntity<String> createEntityWithBody(String body){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    @Test
    void findAllTasks_ReturnsPageOfTask_WhenSuccessful(){
        taskRepository.save(TaskCreator.createTaskWithoutId(user));
        taskRepository.save(TaskCreator.createTaskWithoutId(user));
        TaskResponse expectedTask = TaskCreator.createTaskResponse();
        int expectedSize = (int) taskRepository.count();

        ResponseEntity<PageableResponse<TaskResponse>> response = testRestTemplate.exchange(PATH, HttpMethod.GET,
                createEntity(), new ParameterizedTypeReference<>(){});
        PageableResponse<TaskResponse> body = response.getBody();
        // task ordenado pela data, logo primeiro registrado = ultimo retornado
        TaskResponse firstTaskRegister = body.toList().get(expectedSize-1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(body);
        Assertions.assertEquals(expectedSize, body.getSize());
        Assertions.assertEquals(expectedTask.getId(), firstTaskRegister.getId());
        Assertions.assertEquals(expectedTask.getTask(), firstTaskRegister.getTask());
    }

    @Test
    void findAllTasksComplete_ReturnsPageOfTask_WhenSuccessful(){
        taskRepository.save(TaskCreator.createTaskWithCompleteTrue(user));
        taskRepository.save(TaskCreator.createTaskWithoutId(user));
        long expectedSize = 1;
        String pathParam = String.format("%s?complete=true", PATH);

        ResponseEntity<PageableResponse<TaskResponse>> response = testRestTemplate.exchange(pathParam, HttpMethod.GET,
                createEntity(), new ParameterizedTypeReference<>(){});
        PageableResponse<TaskResponse> body = response.getBody();
        TaskResponse taskResponse = body.toList().get(0);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(body);
        Assertions.assertEquals(expectedSize, body.getSize());
        Assertions.assertTrue(taskResponse.isComplete());
    }

    @Test
    void findAllTasksByNameLike_ReturnsPageOfTask_WhenSuccessful(){
        Task expectedTask = TaskCreator.createTaskWithoutId(user);
        expectedTask.setTask("reuniaoTeste");
        taskRepository.save(expectedTask);
        taskRepository.save(TaskCreator.createTaskWithoutId(user));
        // busca pela task 'reuniao' deve possuir uma correspondencia
        long expectedSize = 1;
        String pathParam = String.format("%s?task=reuniao", PATH);

        ResponseEntity<PageableResponse<TaskResponse>> response = testRestTemplate.exchange(pathParam, HttpMethod.GET,
                createEntity(), new ParameterizedTypeReference<>(){});
        PageableResponse<TaskResponse> body = response.getBody();
        TaskResponse taskResponse = body.toList().get(0);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(body);
        Assertions.assertEquals(expectedSize, body.getSize());
        Assertions.assertEquals(expectedTask.getTask(), taskResponse.getTask());

        // busca pela task 'teste' deve possuir duas correspondencias
        expectedSize = 2;
        pathParam = String.format("%s?tasks=teste", PATH);
        response = testRestTemplate.exchange(pathParam, HttpMethod.GET,
                createEntity(), new ParameterizedTypeReference<>(){});
        body = response.getBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(body);
        Assertions.assertEquals(expectedSize, body.getSize());
    }

    @Test
    void findAllTasksByDate_ReturnsPageOfTask_WhenSuccessful(){
        Task expectedTask = TaskCreator.createTaskWithoutId(user);
        Task taskWithPreviousDate = TaskCreator.createTaskWithoutId(user);
        taskWithPreviousDate.setDate(LocalDateTime.now().minusDays(1));
        Task taskWithLaterDate = TaskCreator.createTaskWithoutId(user);
        taskWithLaterDate.setDate(LocalDateTime.now().plusDays(1));

        taskRepository.save(expectedTask);
        taskRepository.save(taskWithPreviousDate);
        taskRepository.save(taskWithLaterDate);

        long expectedSize = 1;
        String pathParam = String.format("%s?date=%s", PATH, expectedTask.getDate().toLocalDate());

        ResponseEntity<PageableResponse<TaskResponse>> response = testRestTemplate.exchange(pathParam, HttpMethod.GET,
                createEntity(), new ParameterizedTypeReference<>(){});

        PageableResponse<TaskResponse> body = response.getBody();
        TaskResponse taskResponse = body.toList().get(0);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(body);
        Assertions.assertEquals(expectedSize, body.getSize());
        Assertions.assertEquals(expectedTask.getTask(), taskResponse.getTask());
        Assertions.assertEquals(expectedTask.getDate(), taskResponse.getDate());
        Assertions.assertNotEquals(expectedTask.getDate(), taskWithLaterDate.getDate());
        Assertions.assertNotEquals(expectedTask.getDate(), taskWithPreviousDate.getDate());
    }

    @Test
    void createTask_ReturnsTaskResponse_WhenSuccessful() throws JSONException {
        TaskResponse expectedTask = TaskCreator.createTaskResponse();
        TaskPostRequest taskRequest = TaskCreator.createTaskPostRequest();
        JSONObject json = new JSONObject();
        json.put("task", taskRequest.getTask());
        json.put("complete", taskRequest.isComplete());
        json.put("date", taskRequest.getDate());

        ResponseEntity<TaskResponse> response = testRestTemplate.postForEntity("/tasks", createEntityWithBody(json.toString()),
                TaskResponse.class);
        TaskResponse taskResponse = response.getBody();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedTask.getId(), taskResponse.getId());
        Assertions.assertEquals(expectedTask.getTask(), taskResponse.getTask());
    }

    @Test
    void updateTask_ReturnsTaskResponse_WhenSuccessful() throws JSONException {
        taskRepository.save(TaskCreator.createTaskWithoutId(user));
        TaskResponse originalTask = TaskCreator.createTaskResponse();
        TaskPutRequest taskRequest = TaskCreator.createTaskPutRequest();
        JSONObject json = new JSONObject();
        json.put("id", taskRequest.getId());
        json.put("task", taskRequest.getTask());
        json.put("complete", taskRequest.isComplete());
        json.put("date", taskRequest.getDate());

        ResponseEntity<TaskResponse> response = testRestTemplate.exchange("/tasks", HttpMethod.PUT,
                createEntityWithBody(json.toString()), new ParameterizedTypeReference<>() {});
        TaskResponse taskResponse = response.getBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertNotNull(taskResponse.getId());
        Assertions.assertEquals(taskRequest.getTask(), taskResponse.getTask());
        Assertions.assertEquals(originalTask.getId(), taskResponse.getId());
        Assertions.assertNotEquals(originalTask.getTask(), taskResponse.getTask());
        Assertions.assertNotEquals(originalTask.isComplete(), taskResponse.isComplete());
    }

    @Test
    void deleteTask_RemovesTask_WhenSuccessful(){
        Task taskSaved = taskRepository.save(TaskCreator.createTaskWithoutId(user));
        String pathParam = String.format("%s/%s", PATH, taskSaved.getId());

        ResponseEntity<Void> response = testRestTemplate.exchange(pathParam, HttpMethod.DELETE, createEntity(),
                new ParameterizedTypeReference<>() {});

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void userIsOwner_ThrowsPermissionIsMissingException_WhenUserIsNotOwner(){
        User ownerUser = UserCreator.createUserWithoutId();
        ownerUser.setUsername("Owner");
        Task task = TaskCreator.createTaskWithoutId(ownerUser);
        task.setTask("OwnerTask");
        userRepository.save(ownerUser);
        taskRepository.save(task);

        String pathParam = String.format("%s/%s", PATH, task.getId());
        // faz a requisição autenticando com o usuario que não é dono da tarefa, logo sem permissão de acesso.
        ResponseEntity<Void> response = testRestTemplate.exchange(pathParam, HttpMethod.DELETE, createEntity(),
                new ParameterizedTypeReference<Void>() {});

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void findTaskById_ThrowsEntityNotFoundException_WhenTaskIsNotFound(){
        // nenhuma tarefa foi salva no banco de dados, logo tarefa com id = 1 não existe
        long taskIdNotValid = 1;

        String pathParam = String.format("%s/%s", PATH, taskIdNotValid);
        ResponseEntity<Void> response = testRestTemplate.exchange(pathParam, HttpMethod.DELETE, createEntity(),
                new ParameterizedTypeReference<Void>() {});

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}