package com.performance.model;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class dataAPI {

	public static int employeesSize = 1000000;
    public Employee [] employees = new Employee[employeesSize];
    public Organization [] organizations = new Organization[1000];
    public dataAPI(){
        try {
            CSVReader reader = new CSVReader(new FileReader("src/main/resources/Organization3.csv"));
            String [] nextLine;
            nextLine = reader.readNext();
            for(int i=0;i<1000;i++){
                nextLine = reader.readNext();
                organizations[i] = new Organization();
                organizations[i].setID(Integer.valueOf(nextLine[0]));
                organizations[i].setName(nextLine[1]);
                organizations[i].setAcronym(nextLine[2]);
                organizations[i].setEmployeeNumber(nextLine[3]);
            }
            reader.close();

            reader = new CSVReader(new FileReader("src/main/resources/Employee3.csv"));
            nextLine = reader.readNext();
            for(int j=0;j<employeesSize;j++){
                nextLine = reader.readNext();
                System.out.println(j);
                System.out.println(nextLine);
                employees[j] = new Employee();
                employees[j].setID(Integer.valueOf(nextLine[0]));
                employees[j].setName(nextLine[1]);
                employees[j].setPassword(nextLine[2]);
                employees[j].setAge(Integer.valueOf(nextLine[3]));
                employees[j].setOrganization(organizations[Integer.valueOf(nextLine[4])-1]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    public  List getEmployeesFirst(int x){
        Employee [] firstEmployees = new Employee[x];
        for(int i=0; i<x;i++)
            firstEmployees[i] = employees[i];
        return Arrays.asList(firstEmployees);
    }

    public  List getOrganizationsFirst(int x){
        Organization [] firstOrganizations = new Organization[x];
        for(int i=0; i<x;i++)
            firstOrganizations[i] = organizations[i];
        return Arrays.asList(firstOrganizations);
    }
    public Employee getEmployee(int index){
        return employees[index];
    }
    public Organization getOrganization(int index){
        return organizations[index];
    }



}
