package id.my.hendisantika.springbootharperdb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-harperDB
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 4/29/24
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {

    private final ConnectionService connectionService;

    public List<HashMap<String, String>> getAllLeavesForEmployee(String empId) {
        log.info("Getting all leaves for employee - {}", empId);
        List<HashMap<String, String>> resultList = new ArrayList<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM employee_leaves.leaves WHERE empid = ?");
            statement.setString(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, String> result = new HashMap<>();
                result.put("date_of_apply", new Date(Long.parseLong(resultSet.getString("__createdtime__"))).toString());
                result.put("last_update_date", new Date(Long.parseLong(resultSet.getString("__updatedtime__"))).toString());
                result.put("leave_applied_for", resultSet.getString("date"));
                result.put("employee_id", resultSet.getString("empId"));
                resultList.add(result);
            }
            conn.close();
        } catch (Exception e) {
            log.error("Error occurred", e);
            HashMap<String, String> result = new HashMap<>();
            result.put("Error", e.getMessage());
            resultList.add(result);
        }
        return resultList;
    }
}
