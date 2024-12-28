package commissionapp;
import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 *
 * @author user
 */
public class ClientApp extends Thread {

    @Override
    public void run() {

//request  user input for employee ID, it can be int or string.
        System.out.println("Enter Sales Employ ID:");
        Scanner scan = new Scanner(System.in);
        String EmpID = scan.next();
//request  user input for Number of Laptops Sold by the Employee.
        System.out.println("Enter Number of Laptops Sold by the Employee:");
        String NoLaptopsSolds;
//  here we i used while loop to make sure user entered valid number not strings,
//  it will loop until he enter valid number.
        while (true) {
            NoLaptopsSolds = scan.next();
            try {
                Integer.parseInt(NoLaptopsSolds);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid Number of Laptops Sold!!!");
            }

        }
        String response = SendToServer(EmpID, Integer.parseInt(NoLaptopsSolds));

        System.out.println("Server Response is :     " + response);

    }

    public static void main(String[] args) {

        Thread clientThread = new ClientApp();
        clientThread.start();
    }

    private String SendToServer(String EmpID, int LaptopsSolds) {
        String response = "";
        Socket s;
        try {
            s = new Socket("localhost", 499);

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(LaptopsSolds);
            pr.flush();
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            response = bf.readLine();
            in.close();
            bf.close();
            pr.close();
            s.close();
        } catch (IOException ex) {
            System.out.println("errorr: " + ex.getMessage());
        }
        return response;
    }

}
