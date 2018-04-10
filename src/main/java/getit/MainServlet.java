package getit;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by nemavasi on 4/10/18.
 */
public class MainServlet extends HttpServlet {
    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        // Set response content type
//        response.setContentType("text/html");
//
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
       // out.println("<h1>" + message + "</h1>");


        String content;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://free-proxy-list.net");
        httpGet.addHeader("User-Agent", "Mozilla/5.0");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        System.out.println("GET Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer res = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            res.append(inputLine);
        }
        reader.close();

        // print result
        content = res.toString();
        httpClient.close();

        out.println(content);
    }

    public void destroy() {
        // do nothing.
    }

}
