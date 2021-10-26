package Handlers;


import java.io.*;
import java.net.*;
import java.sql.Connection;

import Request.PersonRequest;
import Result.PersonResult;
import Service.PersonService;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
public class PersonHandler implements HttpHandler {


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
                    PersonRequest request;

                    if (urlPath.equals("/person")) {
                        request = new PersonRequest(authToken);
                    }
                    else {
                        String personID = urlPath.substring(8);
                        request = new PersonRequest(authToken, personID);
                    }

                    PersonService service = new PersonService();
                    PersonResult result = service.person(request);

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
