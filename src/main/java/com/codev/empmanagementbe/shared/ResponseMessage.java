package com.codev.empmanagementbe.shared;

import com.codev.empmanagementbe.employee.Employee;

import java.util.List;

public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
