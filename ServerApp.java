package commissionapp;
import java.io.*;
import java.net.*;
import java.sql.*;


public class ServerApp extends Thread {

    private final Socket socket;

    public ServerApp(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            System.out.println("Cleint connected");
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            PrintWriter pr = new PrintWriter(socket.getOutputStream());

            String LaptopsSolds = bf.readLine();
            int NoLaptopsSolds = Integer.parseInt(LaptopsSolds);//checking of valid integer is done in clinet app, however NumberFormatException is handled. 
            if (NoLaptopsSolds > 0) {
                int unitPrice = 90;
                // Calculate sales profit
                int salesProfit = NoLaptopsSolds * unitPrice;
                float chargeRate = getChargeRate(salesProfit);
                double commission = salesProfit * chargeRate;

                pr.println("Sales Profit: " + salesProfit
                        + ", Charge Rate: " + (int) (chargeRate * 100) + "%"
                        + ", Commission: " + commission);
                pr.flush();
            } else {
                pr.println(" Number of sold laptops can not be less then 0!. ");
                pr.flush();
            }
            System.out.println("result sent to client.");

            in.close();
            bf.close();
            pr.close();
            socket.close();
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Server calculation error: " + ex.getMessage());
        }

    }

    private float getChargeRate(int salesProfit) {
        int ChargeCode = 3;
        if (salesProfit > 20000) {
            ChargeCode = 1;
        }
        if (salesProfit > 10000 && salesProfit < 20000) {
            ChargeCode = 2;
        }

        System.out.println("Determined Charge Code: " + ChargeCode); // Debug log
//
        try {

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/SharafDG", "root", "root");
            PreparedStatement statement = con.prepareStatement("SELECT CHARGERATE FROM CHARGERATES WHERE CHARGECODE = " + ChargeCode);

          
            ResultSet resultSet = statement.executeQuery();
             if (resultSet.next()) {
                float chargeRate = resultSet.getFloat("CHARGERATE");
                System.out.println("Retrieved Charge Rate: " + chargeRate); // Debug log
                return chargeRate;
            } else {
                System.err.println("No charge rate found for Charge Code: " + ChargeCode);
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return 0; // Default charge rate if error occurs
    }

    public static void main(String[] args) throws ClassNotFoundException {
        int port = 499;
        //Class.forName("org.apache.derby.client.ClientAutoloadedDriver");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running...");

            while (true) {
                Socket s = serverSocket.accept();
                Thread serverThread = new ServerApp(s);
                serverThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
