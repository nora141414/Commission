package commissionapp;

import java.util.HashMap;
import java.util.Scanner;

class UtilityClass {

    private HashMap<String, Integer> salesEmployeeData = new HashMap<String, Integer>();

    // Adds a new emp sales data entry to the hashmap
    public void addEmpSalesData(String empID, int saleProfit) {
        synchronized (salesEmployeeData) {
            salesEmployeeData.put(empID, saleProfit);
            System.out.println("Emplyee and SalesProfit data added: " + empID + " " + salesEmployeeData.get(empID));
        }
    }

    // Searches for specific emp sales data in the list
    public String searchSalesData(String data) {

        if (salesEmployeeData.containsKey(data)) {
            return "Data Found:" + salesEmployeeData.get(data);
        } else {
            return " Not Found";
        }

    }

    // Displays all the sales data entries in the list
    public void displayAllData() {

        if (salesEmployeeData.isEmpty()) {
            System.out.println("No Emplyee sales data available.");
        } else {
            System.out.println("Emplyee Sales Data:");
            System.out.println("Emplyee ID      Sales Profit");
            for (String i : salesEmployeeData.keySet()) {
                System.out.println("   " + i + "                  " + salesEmployeeData.get(i));
            }
        }

    }
}

public class MenuApp {

    public static void main(String[] args) {
        UtilityClass utility = new UtilityClass();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Sales Employee Data");
            System.out.println("2. Search Sales Employee Data");
            System.out.println("3. Display All Sales Employee Data");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter Employee ID: ");
                    String empID = scanner.nextLine();
                    System.out.print("Enter Employee Sales Profit: ");
                    int SalesProfit = 0;
                    try {
                        SalesProfit = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.print("invalid Number!. exit... ");
                        break;
                    }

                    utility.addEmpSalesData(empID, SalesProfit);
                    break;
                }
                case 2: {
                    System.out.print("Enter Employee ID  to Search: ");
                    String data = scanner.nextLine();
                    System.out.println(utility.searchSalesData(data));
                    break;
                }
                case 3: {
                    utility.displayAllData();
                    break;
                }
                case 4: {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default: {
                    System.out.println("Invalid Choice. Please try again.");
                }
            }
        }
    }
}
