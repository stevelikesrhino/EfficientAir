package com.ee542.iotgarbagecollector.database;

import com.ee542.iotgarbagecollector.database.Node;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface NodeRepository extends ListCrudRepository<Node, Long> {
    @Query("SELECT * FROM nodes")
    List<Node> findAllItems();
}
