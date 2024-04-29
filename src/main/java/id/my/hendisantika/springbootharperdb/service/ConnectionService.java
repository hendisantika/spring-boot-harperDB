package id.my.hendisantika.springbootharperdb.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-harperDB
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 4/29/24
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConnectionService {
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:harperdb:Server=https://xxx.harperdbcloud.com;User=xxx;Password=xxx;UseSSL=true;");
    }
}
