import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Advertisement;
import entity.SiteReader;
import server.ServerClientDialog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        ServerSocket server = new ServerSocket(8080);

        while (!server.isClosed()){
            Socket client=server.accept();
            executorService.execute(new ServerClientDialog(client));
        }


    }
}
