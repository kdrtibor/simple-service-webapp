package com.example;
import org.eclipse.jetty.http.HttpStatus;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException,IOException{

            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println("Tibi's server");
            String query = req.getQueryString();

            String[] pairs = query.split("&");
            //further split the query
            boolean foundFrom = false;
            boolean foundTo = false;
            int lowerLimit = 0;
            int upperLimit = 0;
            for(int i = 0; i < pairs.length; i++){
                String[] keys = pairs[i].split("=");
                if(keys[0].equals("from")){
                    try{
                        lowerLimit = Integer.parseInt(keys[1]);
                        foundFrom = true;
                    }
                    catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }
                if(keys[0].equals("to")){
                    try{
                        upperLimit = Integer.parseInt(keys[1]);
                        foundTo = true;
                    }
                    catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }
            }
            //count up the values

            if(foundFrom && foundTo && lowerLimit < upperLimit){
                for(int i = lowerLimit; i <= upperLimit; i++){
                    resp.getWriter().println(i);
                }
            }
            else{
                resp.getWriter().println("Sorry i don't get what you're asking from me :(");
            }

    }

}
