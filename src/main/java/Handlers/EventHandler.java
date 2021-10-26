package Handlers;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import Request.EventRequest;
import Result.EventResult;
import Service.EventService;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class EventHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String urlPath = exchange.getRequestURI().toString();
                    EventRequest request;

                    if (urlPath.equals("/event")) {
                        request = new EventRequest(authToken);
                    }
                    else {
                        String eventID = urlPath.substring(7);
                        request = new EventRequest(authToken, eventID);
                    }

                    EventService service = new EventService();
                    EventResult result = service.event(request);

                    if (result.getSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }

                    OutputStream resBody = exchange.getResponseBody();
                    StringBuilder response = new StringBuilder();
                    gson.toJson(result, response);

                    writeString(response.toString(), resBody);
                    resBody.close();

                    success = true;
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
