package com.example;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class EmbeddedJettyMain {

    public static void main(String[] args) {

        Server server = new Server(7070);
        ServletContextHandler handler = new ServletContextHandler(server,"/example");
        handler.addServlet(ExampleServlet.class,"/");
        try{
            server.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
