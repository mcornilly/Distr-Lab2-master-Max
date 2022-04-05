import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class REST_Server {
    HttpServer server; // http server for REST
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10); // Execute thread

    public REST_Server() throws IOException { //constructor
        this.server = HttpServer.create(new InetSocketAddress("localhost", 8081), 0);; // Create a httpServer
        this.server.setExecutor(threadPoolExecutor); // set threadPoolExecutor

    }
    public void addContext(String path, HttpHandler handler){
        server.createContext(path, handler); // Create context
        // HttpContext represents a mapping from a URI path to a exchange handler on this HttpServer.
        // Once created, all requests received by the server for the path will be handled by calling the given handler object
    }
    public void start(){
        this.server.start();   //start the server
    }
}
