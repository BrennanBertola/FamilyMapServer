package Handlers;

import java.io.*;
import java.net.*;
import java.util.Locale;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import Result.ClearResult;
import Service.ClearService;

import Result.LoadResult;
import Request.LoadRequest;
import Service.LoadService;

public class LoadHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //clears table, if clear was a failure returns results and exits
                ClearService clearService = new ClearService();
                ClearResult clearResult = clearService.clear();
                if (!clearResult.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    StringBuilder response = new StringBuilder();
                    gson.toJson(clearResult, response);

                    System.out.println(response.toString());
                    writeString(response.toString(), resBody);
                    resBody.close();
                    return;
                }

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                LoadRequest request = (LoadRequest) gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

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

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
