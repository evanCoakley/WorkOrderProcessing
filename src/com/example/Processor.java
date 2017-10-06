package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;



public class Processor {
    Map<Status, HashSet<WorkOrder>> workOrderMap = new HashMap();

    public Map<Status, HashSet<WorkOrder>> getWorkOrderMap() {
        return workOrderMap;
    }
    public void intitializeWorkOrders(){
        workOrderMap.put(Status.INITIAL, new HashSet<>());
        workOrderMap.put(Status.ASSIGNED, new HashSet<>());
        workOrderMap.put(Status.IN_PROGRESS, new HashSet<>());
        workOrderMap.put(Status.DONE, new HashSet<>());
    }

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.intitializeWorkOrders();
        processor.processWorkOrders();

    }


    public void processWorkOrders() {

        try {
            while (true) {
                readAndLoadWorkOrders();
                updateStatus();
                Thread.sleep(5000l);
            }
        } catch (InterruptedException e) {
            System.out.println("Caught: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Caught: " + e.getMessage());
        }
    }



    private void updateStatus() {
        // move work orders in map from one state to another

        Set<WorkOrder> workOrderStatusInitial = workOrderMap.get(Status.INITIAL);
        Set<WorkOrder> workOrderStatusAssigned = workOrderMap.get(Status.ASSIGNED);
        Set<WorkOrder> workOrderStatusInProgress = workOrderMap.get(Status.IN_PROGRESS);

        workOrderMap.put(Status.INITIAL, new HashSet<>());
        workOrderMap.put(Status.ASSIGNED, new HashSet<>());
        workOrderMap.put(Status.IN_PROGRESS, new HashSet<>());

        workOrderMap.put(Status.ASSIGNED, (HashSet<WorkOrder>) workOrderStatusInitial);

        workOrderMap.put(Status.IN_PROGRESS, (HashSet<WorkOrder>) workOrderStatusAssigned);

        // TODO: grab what was in DONE and ADD IN_PROG to it
        workOrderMap.put(Status.DONE, (HashSet<WorkOrder>) workOrderStatusInProgress);

        System.out.println(workOrderMap);
    }


    private void readAndLoadWorkOrders() throws Exception {
        // read the json files into WorkOrders and put in map
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();

        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrder order = mapper.readValue(new File(f.getName()), WorkOrder.class);
                    Status orderStatus = order.getStatus();

                    orderInMap(orderStatus, order);

                    f.delete();
                    System.out.println(workOrderMap);
                } catch (Exception e){
                    System.out.println("Caught: " + e.getMessage());
                }
            }
        }



    }
    private void orderInMap(Status status, WorkOrder workOrder) {
        Set<WorkOrder> workOrderSet = workOrderMap.get(status);
        workOrderSet.add(workOrder);
        workOrderMap.put(status, (HashSet<WorkOrder>) workOrderSet);
    }
}