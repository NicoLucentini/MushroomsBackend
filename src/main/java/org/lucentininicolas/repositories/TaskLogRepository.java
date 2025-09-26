package org.lucentininicolas.repositories;

import org.lucentininicolas.entities.TaskLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskLogRepository extends MongoRepository<TaskLog, String> {
}
