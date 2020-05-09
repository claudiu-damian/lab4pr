import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static Logger LOG = LogManager.getLogger(Client.class);

    public static void main(String[] args) {
        transferDataToServer();
    }

    private static void transferDataToServer() {
        Scanner scanner = new Scanner(System.in);
        byte[] buffer;
        while (true) {
            String userInput = scanner.nextLine();
            buffer = userInput.getBytes();
            try {
                returnSocket().send(returnDataPacket(buffer, returnHost()));
            } catch (IOException e) {
                LOG.error("Could not transfer the information: " + e + "\n");
            }
            if (userInput.equals(ConfigurationConstants.EXIT_MESSAGE))
                break;
        }
    }

    private static DatagramPacket returnDataPacket(byte[] buffer, InetAddress host) {
        return new DatagramPacket(buffer, buffer.length, host, ConfigurationConstants.PORT_NUMBER);
    }

    private static InetAddress returnHost() {
        InetAddress host = null;
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            LOG.error("Cannot obtain the host address: " + e + "\n");
        }
        return host;
    }

    private static DatagramSocket returnSocket() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            LOG.error("Cannot initialize DatagramSocket object: " + e + "\n");
        }
        return datagramSocket;
    }
}
