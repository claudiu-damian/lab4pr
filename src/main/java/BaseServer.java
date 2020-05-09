import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class BaseServer {
    private static Logger LOG = LogManager.getLogger(BaseServer.class);

    public static void main(String[] args) {
        DatagramSocket datagramSocket = getDatagramSocket();
        byte[] receivedData = new byte[65535];
        retrieveClientMessages(datagramSocket, receivedData);
    }

    private static void retrieveClientMessages(DatagramSocket datagramSocket, byte[] receivedData) {
        while (true) {
            dataRetriever(datagramSocket, receivedData);
            LOG.info("Message from the Client: [" + dataProcessor(receivedData) + "]\n");
            if (dataProcessor(receivedData).toString().equals(ConfigurationConstants.EXIT_MESSAGE)) {
                LOG.info("Exiting. Client sent the exit message: [" + ConfigurationConstants.EXIT_MESSAGE + "]\n");
                break;
            }
            receivedData = new byte[65535];
        }
    }

    private static void dataRetriever(DatagramSocket datagramSocket, byte[] receivedData) {
        DatagramPacket datagramPacketReceiver;
        datagramPacketReceiver = new DatagramPacket(receivedData, receivedData.length);
        try {
            datagramSocket.receive(datagramPacketReceiver);
        } catch (IOException e) {
            LOG.error("Cannot receive the data: " + e);
        }
    }

    private static DatagramSocket getDatagramSocket() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(ConfigurationConstants.PORT_NUMBER);
        } catch (SocketException e) {
            LOG.error("Cannot create the DatagramSocket object: " + e);
        }
        return datagramSocket;
    }

    private static StringBuilder dataProcessor(byte[] data) {
        if (data == null)
            return null;
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (data[i] != 0) {
            builder.append((char) data[i]);
            i++;
        }
        return builder;
    }
}
