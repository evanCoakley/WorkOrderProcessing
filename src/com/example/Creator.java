package com.example;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Creator {

    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
        creator.loopWorkOrders();

    }

    public void createWorkOrders() {
        // read input, create work orders and write as json files


        WorkOrder workOrder = new WorkOrder();
        Scanner scanner = new Scanner(System.in);

        workOrder.setStatus(Status.INITIAL);
        System.out.println("Enter ID:  ");
        workOrder.setId(scanner.nextInt());
        System.out.println("Enter Name:  ");
        workOrder.setSenderName(scanner.next());
        System.out.println("Enter Work Order Description:  ");
        workOrder.setDescription(scanner.next());


        ObjectMapper mapper = new ObjectMapper();

        File file = new File(workOrder.getId() + ".json");

        try {
            mapper.writeValue(file, workOrder);
        }  catch (Exception e){
            System.out.println("Caught: " + e.getMessage());
        }

    }

    public void loopWorkOrders() {
        try {
            while (true) {
                createWorkOrders();
                Thread.sleep(5000l);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}