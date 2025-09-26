package org.lucentininicolas.entities;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "task_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskLog {
    @Id
    private String id;

    private Integer taskId;
    private String details;
    private LocalDateTime timestamp = LocalDateTime.now();
}