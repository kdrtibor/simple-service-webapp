package com.example;

import com.example.rest.config.MarshallingFeature;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class App {

    public static void main(String[] args)
            throws Exception {

        ResourceConfig config = new ResourceConfig();
        config.packages("com.example.rest");
        config.register(MarshallingFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server jettyServer = new Server(8085);
        ServletContextHandler context = new ServletContextHandler(jettyServer, "/api/*");
        context.addServlet(servlet, "/*");

        servlet.setInitParameter("jersey.config.server.provider.classnames",
                "org.glassfish.jersey.filter.LoggingFilter");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

}
