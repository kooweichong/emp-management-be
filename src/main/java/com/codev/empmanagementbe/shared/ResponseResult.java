package com.codev.empmanagementbe.shared;

import com.codev.empmanagementbe.employee.Employee;

import java.util.List;

public class ResponseResult {
    private List<Employee> results;

    public ResponseResult(List<Employee> employeeList) {
        this.results = employeeList;
    }

    public List<Employee> getResults() {
        return results;
    }

    public void setResults(List<Employee> results) {
        this.results = results;
    }
}
