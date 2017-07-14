package com.example;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class EmbeddedJettyMain {

    public static void main(String[] args) {

        //Dynamic
        Server server = new Server(8082);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server,"/dynamic");
        servletContextHandler.addServlet(ExampleServlet.class,"/");

        //Static
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("C:\\simple-service-webapp\\src\\main\\resources");
       // resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        ContextHandler contextHandler = new ContextHandler("/static"); /* the server uri path */
        contextHandler.setHandler(resourceHandler);

        // Add handlers
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { contextHandler, servletContextHandler });
        server.setHandler(handlers);
        try{
            server.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
