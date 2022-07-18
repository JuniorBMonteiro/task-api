package br.com.bmont.task.service;

import br.com.bmont.task.mapper.TaskMapper;
import br.com.bmont.task.model.Task;
import br.com.bmont.task.repository.TaskRepository;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskResponse> findAllTasks(Pageable pageable){
        Page<Task> tasks = taskRepository.findAll(pageable);
        return TaskMapper.toTaskResponse(tasks);
    }

    public TaskResponse createTask(TaskPostRequest taskPostRequest) {
        Task task = new Task();
        task.setTask(taskPostRequest.getTask());
        task.setComplete(taskPostRequest.isComplete());
        task.setDate(taskPostRequest.getDate());
        return TaskMapper.toTaskResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(TaskPutRequest taskPutRequest) {
        Task taskSaved = findTaskByIdOrThrowException(taskPutRequest.getId());
        taskSaved.setTask(taskPutRequest.getTask());
        taskSaved.setComplete(taskPutRequest.isComplete());
        taskSaved.setDate(taskPutRequest.getDate());
        taskRepository.save(taskSaved);
        return TaskMapper.toTaskResponse(taskSaved);
    }


    public void deleteTask(long taskId) {
        Task taskSaved = findTaskByIdOrThrowException(taskId);
        taskRepository.delete(taskSaved);
    }

    public Task findTaskByIdOrThrowException(long taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
