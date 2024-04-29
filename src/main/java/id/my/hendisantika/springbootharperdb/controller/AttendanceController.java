package id.my.hendisantika.springbootharperdb.controller;

import id.my.hendisantika.springbootharperdb.dto.EmployeeDTO;
import id.my.hendisantika.springbootharperdb.dto.EmployeeEditDataDTO;
import id.my.hendisantika.springbootharperdb.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-harperDB
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 4/29/24
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping(value = "/api/get/all/leaves/{employeeId}")
    public List<HashMap<String, String>> getAllLeavesForEmployee(@PathVariable String employeeId) {
        return attendanceService.getAllLeavesForEmployee(employeeId);
    }

    @PostMapping(value = "/api/add/leave")
    public HashMap<String, String> addNewLeave(@RequestBody EmployeeDTO employeeData) {
        return attendanceService.addNewLeaveForEmployee(employeeData);
    }

    @PutMapping(value = "/api/edit/leave")
    public HashMap<String, String> editLeave(@RequestBody EmployeeEditDataDTO employeeEditData) {
        return attendanceService.editLeaveForEmployee(employeeEditData);
    }

    @DeleteMapping(value = "/api/cancel/leave")
    public HashMap<String, String> cancelLeave(@RequestBody EmployeeDTO employeeData) {
        return attendanceService.cancelLeaveForEmployee(employeeData);
    }
}

