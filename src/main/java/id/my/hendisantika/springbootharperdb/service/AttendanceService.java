package id.my.hendisantika.springbootharperdb.service;

import id.my.hendisantika.springbootharperdb.dto.EmployeeDTO;
import id.my.hendisantika.springbootharperdb.dto.EmployeeEditDataDTO;
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

    public HashMap<String, String> addNewLeaveForEmployee(EmployeeDTO employeeData) {
        log.info("Inserting new leave for employee - {}", employeeData.getEmployeeId());
        HashMap<String, String> result = new HashMap<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO employee_leaves.leaves (date, empid) VALUES (?,?)");
            statement.setString(1, employeeData.getDate());
            statement.setString(2, employeeData.getEmployeeId());
            int count = statement.executeUpdate();
            if (count > 0) {
                result.put("Message", "Success");
                result.put("Affected rows", String.valueOf(count));
            }
            conn.close();
        } catch (Exception e) {
            log.error("Error occurred", e);
            result.put("Error", e.getMessage());
        }
        return result;
    }

    public HashMap<String, String> editLeaveForEmployee(EmployeeEditDataDTO employeeEditData) {
        log.info("Updating leave for employee - {}", employeeEditData.getEmployeeId());
        HashMap<String, String> result = new HashMap<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE employee_leaves.leaves SET date = ? WHERE empid=? AND DATE = ?");
            statement.setString(1, employeeEditData.getNewDate());
            statement.setString(2, employeeEditData.getEmployeeId());
            statement.setString(3, employeeEditData.getPreviousDate());
            int count = statement.executeUpdate();
            if (count > 0) {
                result.put("Message", "Success");
                result.put("Affected rows", String.valueOf(count));
            }
            conn.close();
        } catch (Exception e) {
            log.error("Error occurred", e);
            result.put("Error", e.getMessage());
        }
        return result;
    }

    public HashMap<String, String> cancelLeaveForEmployee(EmployeeDTO employeeData) {
        log.info("Cancelling leave for employee - {}", employeeData.getEmployeeId());
        HashMap<String, String> result = new HashMap<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM employee_leaves.leaves WHERE empid = ? AND DATE = ?");
            statement.setString(1, employeeData.getEmployeeId());
            statement.setString(2, employeeData.getDate());
            int count = statement.executeUpdate();
            if (count > 0) {
                result.put("Message", "Success");
                result.put("Affected rows", String.valueOf(count));
            }
            conn.close();
        } catch (Exception e) {
            log.error("Error occurred", e);
            result.put("Error", e.getMessage());
        }
        return result;
    }
}
