package com.codev.empmanagementbe.shared;

import com.codev.empmanagementbe.employee.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = { "Id", "Title", "Description", "Published" };

    public static boolean isCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static boolean isComment(String data)
    {
        if(data.startsWith("#"))
            return true;

        return false;
    }

    // Read CSV file InputStream, return Employee List.
    public static List<Employee> csvToEmployeeList(InputStream inputStream) {
        try (BufferedReader fileReader
                     = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.RFC4180.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
        {
            List<String> headerList = csvParser.getHeaderNames();
            if (headerList.size()!=4)
            {
                throw new Exception("Too many or too less column!");
            }

            List<Employee> employeeList = new ArrayList<Employee>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                // Ignore if the row is comment (Row Starts with #)
                if (isComment(csvRecord.get(0)))
                    continue;

                if (Float.parseFloat(csvRecord.get(3)) < 0)
                    throw new Exception("Salary cannot be less than 0!");;

                Employee employee = new Employee(
                        csvRecord.get(0),
                        csvRecord.get(1),
                        csvRecord.get(2),
                        Float.parseFloat(csvRecord.get(3))
                );

                employeeList.add(employee);
            }

            return employeeList;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException("Fail Reason: " + exception.getMessage());
        }

    }
}
