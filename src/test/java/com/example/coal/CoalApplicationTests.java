package com.example.coal;

import com.example.coal.server.DriverOrderServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class CoalApplicationTests {

    @Test
    void contextLoads() {
        DriverOrderServer driverOrderServer = new DriverOrderServer();
        List<Map<String, Object>> driOrderList = driverOrderServer.getDriOrderList(1);
        System.out.println(driOrderList);
    }

}
