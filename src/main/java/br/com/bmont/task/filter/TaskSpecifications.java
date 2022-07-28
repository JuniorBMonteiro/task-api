package br.com.bmont.task.filter;

import br.com.bmont.task.model.Task;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskSpecifications {
    public static Specification<Task> getFilteredTasks(TaskFilterParam params){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(params.getUser())){
                predicates.add(criteriaBuilder.equal(root.<String>get("user"), params.getUser()));
            }
            if (Objects.nonNull(params.getTask())){
                String like = "%" + params.getTask() + "%";
                predicates.add(criteriaBuilder.like(root.<String>get("task"), like));
            }
            if (Objects.nonNull(params.getComplete())){
                boolean complete = Boolean.parseBoolean(params.getComplete());
                predicates.add(criteriaBuilder.equal(root.get("complete"), complete));
            }
            if (Objects.nonNull(params.getDate())){
                LocalDateTime startDay = LocalDateTime.of(params.getDate(), LocalTime.MIN);
                LocalDateTime endDay = LocalDateTime.of(params.getDate(), LocalTime.MAX);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDay));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDay));
            }
            query.orderBy(criteriaBuilder.desc(root.get("date")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
