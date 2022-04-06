import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class REST_Handler implements HttpHandler {
    protected String contextPath ="/";
    public REST_Handler(){}
    public REST_Handler(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "GET":
                System.out.println("-----GET BEGIN-----");
                handleGET(exchange);
                System.out.println("-----GET END-----");
                break;
            case "PUT":
                System.out.println("-----PUT BEGIN-----");
                handlePUT(exchange);
                System.out.println("-----PUT END-----");
                break;
            case "POST":
                System.out.println("-----POST BEGIN-----");
                handlePOST(exchange);
                System.out.println("-----POST END-----");
                break;
            case "DELETE":
                System.out.println("-----DELETE BEGIN-----");
                handleDELETE(exchange);
                System.out.println("-----DELETE END-----");
                break;
        }
    }

    private void handleGET(HttpExchange httpExchange) throws IOException {
        System.out.println(httpExchange.getRequestURI().toString());
        //get data
        String object = getURI(httpExchange.getRequestURI().toString());
        handleData(httpExchange, object);
    }

    private void handlePOST(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestURI().toString());

        //get body
        String body = inputStreamToString(httpExchange.getRequestBody());

        //get data
        String object = postURI(httpExchange.getRequestURI().toString(),body);
        handleData(httpExchange, object);
    }

    private void handlePUT(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestURI().toString());

        //get body
        String body = inputStreamToString(httpExchange.getRequestBody());
        //get data
        String object = putURI(httpExchange.getRequestURI().toString(), body);
        handleData(httpExchange, object);
    }
    private void handleDELETE(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestURI().toString());
        //get data
        boolean success = deleteURI(httpExchange.getRequestURI().toString());
        if (success){
            httpExchange.sendResponseHeaders(200, 0);
        }else{
            httpExchange.sendResponseHeaders(400,0);
        }
        httpExchange.close();
    }
    void handleData(HttpExchange httpExchange, String URI_String) throws IOException {
        if (URI_String == null) { // If the URI is empty (client error)
            httpExchange.sendResponseHeaders(400,0); //400 - 499 client error response
            httpExchange.getResponseBody().close();
            return;
        }
        try {
            byte[] URI_data = URI_String.getBytes(StandardCharsets.UTF_8); // GET data from URI
            OutputStream outputStream = httpExchange.getResponseBody(); // Outputstream for HTTP
            httpExchange.sendResponseHeaders(200, URI_data.length); // 200 - 299 succesfull resposne
            outputStream.write(URI_data); // write data into the outputstream
            outputStream.flush(); // flush outputstream
            outputStream.close(); // close outputstream
        }catch (IOException e){ // If the server can't send the data to the output stream
            httpExchange.sendResponseHeaders(500,0); // 500 - 599 server error response
            httpExchange.getResponseBody().close();
        }
    }
    private String inputStreamToString(InputStream stream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(stream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }
        return buf.toString(StandardCharsets.UTF_8);
    }
    abstract String getURI(String URI);

    abstract String putURI(String URI,String value);

    abstract String postURI(String URI,String value);

    abstract boolean deleteURI(String URI);
}