package server;

import entity.SiteReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerClientDialog implements Runnable {
    private final Socket client;

    private String response;


    public ServerClientDialog(Socket client) {
        this.client = client;
            }

    /**
     * This method gets request from clent and sends responce to him
     */
    @Override
    public void run() {


        try {

            System.out.println("Client connected. Time is:" + Calendar.getInstance().getTime());
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            String requestUrl = inputStream.readUTF();
           // System.out.println("Read from client message: " + requestUrl);

            outputStream.writeUTF(getResponse(requestUrl));

            outputStream.flush();
            inputStream.close();
            outputStream.close();
            client.close();
            System.out.println("Closing operation! Client was disconnected! Time is: "+  Calendar.getInstance().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method return Json string from URL
     */
    private String getResponse(String Url) {
        SiteReader siteReader = new SiteReader(Url);
        response = siteReader.getJsonString();
      //  System.out.println("Response is "+ response);
        return response;
    }
}
