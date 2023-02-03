package com.codev.empmanagementbe.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE id = ?1")
    Optional<Employee> findEmployeeByID(String id);

    @Query("SELECT e FROM Employee e " +
            "WHERE e.salary BETWEEN ?1 AND ?2")
    Optional<List<Employee>> findEmployeeWithConditions(Float minSalary, Float maxSalary);

}
