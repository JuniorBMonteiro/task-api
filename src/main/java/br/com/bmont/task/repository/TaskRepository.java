package br.com.bmont.task.repository;

import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {

//    Page<Task> findByUserOrderByDate(Pageable pageable);
}
