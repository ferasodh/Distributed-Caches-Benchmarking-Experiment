package com.performance.model;

import java.util.List;

public class testt {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        dataAPI testt = new dataAPI();
        System.out.println("===============Testing get List Of Employees By max index===============");
        List <Employee> list = testt.getEmployeesFirst(11);
        System.out.println(list.get(1).toString());
        System.out.println(list.get(5).toString());
        System.out.println(list.get(10).toString());
        System.out.println("\n\n===============Testing get List Of Organizations By max index===============");
        List <Organization> list2 = testt.getOrganizationsFirst(10);
        System.out.println(list2.get(1).toString());
        System.out.println(list2.get(5).toString());
        System.out.println(list2.get(9).toString());
        System.out.println("\n\n===============Testing get Employee By index===============");
        System.out.println(testt.getEmployee(5000));
        System.out.println("\n\n===============Testing get Organization By index===============");
        System.out.println(testt.getOrganization(5));
    }

}