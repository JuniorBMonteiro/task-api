package br.com.bmont.task.mapper;

import br.com.bmont.task.model.Task;
import br.com.bmont.task.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskResponse toTaskResponse(Task task){
        return new TaskResponse(task.getId(), task.getTask(), task.isComplete(), task.getDate());
    }

    public static Page<TaskResponse> toTaskResponse(Page<Task> task){
        List<TaskResponse> tasks =  task.stream()
                .map(TaskMapper::toTaskResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(tasks);
    }
}
