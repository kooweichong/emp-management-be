package com.codev.empmanagementbe.employee;

import com.codev.empmanagementbe.shared.CSVHelper;
import com.codev.empmanagementbe.shared.ResponseMessage;
import com.codev.empmanagementbe.shared.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<ResponseResult> getEmployees(@RequestParam Float minSalary, @RequestParam Float maxSalary, @RequestParam Integer offset,
                                       @RequestParam Integer limit, @RequestParam String sort)
    {
        if (minSalary<0 || maxSalary<0 || !(sort.startsWith("+") || sort.startsWith("-")) )
        {
            return ResponseEntity.badRequest().body(new ResponseResult(null));
        }
        List<Employee> employeeList = employeeService.getEmployees(minSalary, maxSalary,offset,limit,sort);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseResult(employeeList));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file)
    {
        String message = "";

        // Only process if the header content type is text/csv
        if (CSVHelper.isCSVFormat(file))
        {
            try{
                employeeService.saveEmployeeCSV(file);
                message = "File uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }catch (Exception exception)
            {
                message = "Failed to upload the file: " + file.getOriginalFilename() + " " + exception.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Failed to upload file, File is not csv format!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));

    }
}
