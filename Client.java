// Java implementation for a client 
// Save file as Client.java 
package clientserverimplementation;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) throws IOException {

        try {
            Scanner scn = new Scanner(System.in);
            System.err.println("Please Enter Your Name");
            String Name=scn.nextLine();
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            Socket s = new Socket(ip, 5056);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            new Thread() {
                @Override
                public void run() {
                  //  System.err.println("sENDING tHread");
                    while (true) {
                        Scanner sc = new Scanner(System.in);
                        String ss = sc.nextLine();
                       // System.err.println("Message Entered by Server!");
                        if (ss.equals("Exit")) {
                            System.out.println("Closing this connection : " + s);
                            try {
                                s.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("Connection closed");
                            break;
                        }

                        try {
                            ss=Name+"::"+ss;
                            dos.writeUTF(ss);
                        } catch (IOException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                   // System.err.println("rECIEVING tHread");
                    while (true) {
                        try {
                            String received = null;
                            received = dis.readUTF();
                            System.err.println(received);
                            if (received.equals("Exit")) {
                                System.out.println("Client " + s + " sends exit...");
                                System.out.println("Closing this connection.");
                                s.close();
                                System.out.println("Connection closed");
                                dis.close();
                                dos.close();
                                System.exit(0);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();

//			while (true) 
//			{ 
//				System.out.println(dis.readUTF()); 
//				String tosend = scn.nextLine(); 
//				dos.writeUTF(tosend); 
//				if(tosend.equals("Exit")) 
//				{ 
//					System.out.println("Closing this connection : " + s); 
//					s.close(); 
//					System.out.println("Connection closed"); 
//					break; 
//				} 
//				String received = dis.readUTF(); 
//				System.out.println(received); 
//			} 
        } catch (IOException e) {
        }
    }
}
