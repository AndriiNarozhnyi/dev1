package repos;

import domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository <Message, Long> {
}
