package com.codev.empmanagementbe.employee;

import com.codev.empmanagementbe.shared.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(Float minSalary, Float maxSalary, Integer offset, Integer limit, String sort)
    {
        Optional<List<Employee>> optionalEmpList = employeeRepository.findEmployeeWithConditions(minSalary, maxSalary);

        if (optionalEmpList.isPresent())
        {
            List<Employee> employeeList = optionalEmpList.get();

            if (sort.equals("+id"))
                employeeList.sort(Comparator.comparing(Employee::getId));
            else if (sort.equals("-id"))
                employeeList.sort(Comparator.comparing(Employee::getId).reversed());
            else if (sort.equals("+name"))
                employeeList.sort(Comparator.comparing(Employee::getName));
            else if (sort.equals("-name"))
                employeeList.sort(Comparator.comparing(Employee::getName).reversed());
            else if (sort.equals("+login"))
                employeeList.sort(Comparator.comparing(Employee::getLogin));
            else if (sort.equals("-login"))
                employeeList.sort(Comparator.comparing(Employee::getLogin).reversed());
            else if (sort.equals("+salary"))
                employeeList.sort(Comparator.comparing(Employee::getSalary));
            else if (sort.equals("-salary"))
                employeeList.sort(Comparator.comparing(Employee::getSalary).reversed());

            List<Employee> resultList = employeeList.subList(
                    Math.min(employeeList.size(), offset),
                    Math.min(employeeList.size(), offset+limit)
            );

            return resultList;
        }


        return null;
    }

    public void saveEmployeeCSV(MultipartFile file)
    {
        try{
            List<Employee> employeeList = CSVHelper.csvToEmployeeList(file.getInputStream());

            List<Employee> newEmployeeList = updateEmployeesWithList(employeeList);

            employeeRepository.saveAll(newEmployeeList);
        }catch (IOException exception)
        {
            throw new RuntimeException("Failed to store data: " + exception.getMessage());
        }
    }

    // Pass in an employee and update the employee if it exists in the database (based on id)
    public boolean updateEmployee(Employee employee)
    {
        try{
            Optional<Employee> employeeFound = employeeRepository.findEmployeeByID(employee.getId());

            if (employeeFound.isPresent())
            {
                Employee tempEmployee = employeeFound.get();
                tempEmployee.setLogin(employee.getLogin());
                tempEmployee.setName(employee.getName());
                tempEmployee.setSalary(employee.getSalary());

                employeeRepository.save(tempEmployee);
                return true;
            }

        }catch (Exception exception)
        {
            System.out.println("Failed to update Employee: " + exception.getMessage());
        }
        return false;
    }

    // Pass in employee list and update each employee if it exists in the database
    // Return the employee list which is not in the database
    public List<Employee> updateEmployeesWithList(List<Employee> employeeList)
    {
        List<Employee> resultingList = new ArrayList<Employee>();

        for (Employee employee: employeeList)
        {
            boolean updateResult = updateEmployee(employee);
            if (!updateResult)
                resultingList.add(employee);
        }

        return resultingList;
    }
}
