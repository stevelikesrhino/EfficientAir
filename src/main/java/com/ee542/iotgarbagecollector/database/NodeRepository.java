package com.ee542.iotgarbagecollector.database;

import com.ee542.iotgarbagecollector.entity.Node;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NodeRepository extends ListCrudRepository<Node, Long> {
    @Query("SELECT * FROM nodes")
    List<Node> findAllItems();

    @Modifying
    @Transactional
    @Query("INSERT INTO nodes (time, location, fill, humidity) VALUES (:time_stamp, :location, :fill, :humidity)")
    void insertNode(@Param("time_stamp") Long timeStamp,
                    @Param("location") Short location,
                    @Param("fill") Byte fill,
                    @Param("humidity") Byte humidity);
}
