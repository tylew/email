import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
*  author: Tyler Lewis
*  Email:  tylewis@chapman.edu
*  Date:  2/17/2021
*  version: 3.1
*/
 
class Email {
  public static void main(String[] argv) throws Exception {
    // String send_user;
    // String rcpt_user;
    // String data;
    // String from;
    // String to;

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
    System.out.print("Type the email sender: ");
    final String send_user = inFromUser.readLine();

    System.out.print("Type the email recipient: ");
    final String rcpt_user = inFromUser.readLine();

    System.out.print("Type the sender name: ");
    final String from = inFromUser.readLine();

    System.out.print("Type the recipient name: ");
    final String to = inFromUser.readLine();

    System.out.print("Type the email subject: ");
    final String subject = inFromUser.readLine();

    String data = "FROM: " + from + "\nTO: " + to + "\nSUBJECT: " + subject + "\n";
    String line = "";
    System.out.print("Type email body, end message with '.' on final line:\n");
    line = inFromUser.readLine();

    while (!(line.equals("."))) {
      data += line + "\n";
      line = inFromUser.readLine();
    }

    data += ".";

    // Open socket and prepare to send
    Socket clientSocket = null;

    try {
      System.out.println("Message recieved - Attempting connection to smtp.chapman.edu using port 25...");
      clientSocket = new Socket("smtp.chapman.edu", 25);
    } catch (Exception e) {
      System.out.println("\nError: Failed to open socket connection\n");
      System.exit(0);
    }

    //Send Data
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    outToServer.println("HELO icd.chapman.edu");
    System.out.println("FROM CLIENT: " + "HELO icd.chapman.edu");
    System.out.println("FROM SERVER: " + inFromServer.readLine());

    outToServer.println("MAIL FROM: " + send_user);
    System.out.println("FROM CLIENT: " + "MAIL FROM: " + send_user);
    System.out.println("FROM SERVER: " + inFromServer.readLine());

    outToServer.println("RCPT TO: " + rcpt_user);
    System.out.println("FROM CLIENT: " + "RCPT TO: " + rcpt_user);
    System.out.println("FROM SERVER: " + inFromServer.readLine());

    outToServer.println("DATA");
    System.out.println("FROM CLIENT: " + "DATA");
    System.out.println("FROM SERVER: " + inFromServer.readLine());
    System.out.println("FROM SERVER: " + inFromServer.readLine());
    
    outToServer.println(data);
    System.out.println("FROM CLIENT: " + data);
    System.out.println("FROM SERVER: " + inFromServer.readLine());

    outToServer.println("QUIT");
    System.out.println("FROM CLIENT: " + "QUIT");
    System.out.println("FROM SERVER: " + inFromServer.readLine());
      
    clientSocket.close();
  }
}
