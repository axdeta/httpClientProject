import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpClient {

    public static void main(String[] args) throws Exception {
        //GET HOST AND PORT
        String hostname = Props.getProperties("HOST");
        int port = Integer.parseInt(Objects.requireNonNull(Props.getProperties("PORT")));
        assert hostname != null;

        //CREATE HTTP SERVER
        HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);

        //CUSTOM HANDLER
        server.createContext("/test", new  MyHandler());

        //THREADS POOL
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);

        //START
        server.start();
    }

    //send response for GET
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Repository repository = new Repository();
            if (t.getRequestMethod().equals("POST")){ //REQUEST METHOD IS POST = BUTTON WAS PRESSED
               repository.countUp();
            }
            String response = "<html>\n" +
                    "  <body>\n" +
                    "    <p>\n" +
                    "      COUNT: "+ repository.getCount() +"\n" +
                    "    </p>\n" +
                    "   <form action=\"\" method=\"post\">\n" +
                    "    <input type=\"submit\" name=\"up_number\" value=\"UP NUMBER\" />\n" +
                    "</form>\n" +
                    "  </body>\n" +
                    "</html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

