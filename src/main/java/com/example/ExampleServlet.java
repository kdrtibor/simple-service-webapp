package com.example;

import org.eclipse.jetty.http.HttpStatus;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println("Tibi's server");
        String query = req.getQueryString();
        String[] pairs = query.split("&");

        //initialize local variables
        boolean foundFrom = false;
        boolean foundTo = false;
        int lowerLimit = 0;
        int upperLimit = 0;

        //further split the key and value pairs
        for (String pair : pairs) {
            String[] keys = pair.split("=");
            if (keys[0].equals("from") && !foundFrom) {
                try {
                    lowerLimit = Integer.parseInt(keys[1]);
                    foundFrom = true;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (keys[0].equals("to") && !foundTo) {
                try {
                    upperLimit = Integer.parseInt(keys[1]);
                    foundTo = true;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        //count up the values
        if (foundFrom && foundTo && lowerLimit < upperLimit) {
            for (int i = lowerLimit; i <= upperLimit; i++) {
                resp.getWriter().println(i);
            }
        } else {
            resp.getWriter().println("Sorry i don't get what you're asking from me :(");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder jb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/
           e.printStackTrace();
           return;
        }

        System.out.println("post: " + jb.toString());

        try {
            JSONObject jsonObject = new JSONObject(jb.toString());
            long lowerVal = jsonObject.getLong("from");
            long upperVal = jsonObject.getLong("to");

            for (long i = lowerVal; i < upperVal; i++) {
                resp.getWriter().println(i);
            }

        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string ");
        }

    }
}
