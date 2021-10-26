package Handlers;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import Request.FillRequest;
import Result.FillResult;
import Service.FillService;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String urlPath = exchange.getRequestURI().toString();
                urlPath = urlPath.substring(6);

                String username = urlPath;
                int generations = 4; //default number of generations
                boolean gaveGeneration = false;
                for (int i = 0; i < urlPath.length(); ++i) {
                    if (urlPath.charAt(i) == '/') {
                        username = urlPath.substring(0, i);
                        gaveGeneration = true;
                        break;
                    }
                }


                if(gaveGeneration) {
                    generations = Integer.parseInt(urlPath.substring(username.length() + 1));
                }


                if (username != "") {
                    FillRequest request = new FillRequest(username, generations);
                    FillService service = new FillService();
                    FillResult result = service.fill(request);

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
