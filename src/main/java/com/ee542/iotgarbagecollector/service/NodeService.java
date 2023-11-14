package com.ee542.iotgarbagecollector.service;

import com.ee542.iotgarbagecollector.entity.Node;
import com.ee542.iotgarbagecollector.database.NodeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;
    private final JdbcTemplate jdbcTemplate;
    public NodeService(NodeRepository nodeRepository, JdbcTemplate jdbcTemplate) {
        this.nodeRepository = nodeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Node> getAllNodes() {
        return nodeRepository.findAllItems();
    }

    //TODO: for debug purpose only - pre-generated data
    public List<Node> readDataFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Node> nodes = new ArrayList<>();
        String filename =
                "D:\\Documents\\GitHub\\iotgarbagecollection\\src\\main\\java\\com\\ee542\\iotgarbagecollector\\data\\generated_data";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Since each line is a JSON array with a single object, parse accordingly
                Node[] nodeArray = mapper.readValue(line, Node[].class);
                if (nodeArray.length > 0) {
                    nodes.add(nodeArray[0]);
                }
            }
        }

        return nodes;
    }
    @Transactional
    public void batchInsertNodes(List<Node> nodes) {
        String sql = "INSERT INTO nodes (time, location, fill, humidity) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Node node = nodes.get(i);
                ps.setLong(1, node.time());
                ps.setShort(2, node.location());
                ps.setByte(3, node.fill());
                ps.setByte(4, node.humidity());
            }

            @Override
            public int getBatchSize() {
                return nodes.size();
            }
        });
    }
}
