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

public class Main {

    private final static String SERVER = "127.0.0.1";
    private final static int PORT = 0x2BAD;
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
                System.out.print("H:Head, T:Tail or Q:Quit : ");
                String content = sc.next().toLowerCase();

                if(content.equals("q")) {
                    break;
                }
                else if ("h".equals(content) || "t".equals(content)) {
                    writer.writeBoolean(content.equals("h"));
                    writer.flush();

                    boolean is_head = reader.readBoolean();
                    int score_num = reader.readInt();

                    System.out.printf("It was %s, here are the scores :\n", is_head ? "HEAD" : "TAIL");
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

