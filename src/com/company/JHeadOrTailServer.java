package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class JHeadOrTailServer {

    private final static String SERVER = "127.0.0.1";
    private final static int PORT = 0x2BAE;
    private final static int TIMEOUT = 30000;

    public static void main(String[] args) throws UnknownHostException, IOException {
        SocketAddress addr = new InetSocketAddress(InetAddress.getByName(SERVER), PORT);
        Socket socket = new Socket();
        Scanner sc =new Scanner(System.in);

        try {
            socket.connect(addr, TIMEOUT);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            int num = reader.readInt();

            System.out.printf("You're player %d\n", num);
            for(;;) {
                System.out.print("P:Pierre, F:Feuille, C:Ciseaux or Q:Quit : ");
                String content = sc.next().toUpperCase();

                if(content.equals("Q")) {
                    break;
                }
                else if ("P".equals(content) || "F".equals(content) || "C".equals(content)) {
                    writer.writeChars(content);
                    writer.flush();

                    int score_num = reader.readInt();

                    System.out.printf("Here are the scores :\n");
                    for (var i = 1; i <= score_num; i++) {
                        int score = reader.readInt();

                        System.out.printf("- Player %d%s : %s\n",
                                i, i == num ? " (you)" : "",
                                score >= 0 ? Integer.toString(score) : "-"
                        );
                    }
                }
            }
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

