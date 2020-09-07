package server;

import entity.SiteReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerClientDialog implements Runnable {
    private final Socket client;
    private final String urlSite;
    private String response;


    public ServerClientDialog(Socket client, String url) {
        this.client = client;
        this.urlSite = url;
    }

    /**
     * This method gets request from clent and sends responce to him
     */
    @Override
    public void run() {


        try {
            System.out.println("Client connected.");
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            String request = inputStream.readUTF();
            System.out.println("Read from client message: " + request);

            outputStream.writeUTF(getResponse());

            outputStream.flush();
            inputStream.close();
            outputStream.close();
            client.close();
            System.out.println("Closing operation! Client was disconnected!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method return Json string from URL
     */
    private String getResponse() {
        SiteReader siteReader = new SiteReader(urlSite);
        response = siteReader.getJsonString();
        System.out.println("Response is "+ response);
        return response;
    }
}
