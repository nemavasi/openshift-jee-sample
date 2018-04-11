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
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by nemavasi on 4/10/18.
 */
public class MainServlet extends HttpServlet {

    private Date cacheLastChanged;
    private String cachedContent;

    public void init() throws ServletException {
        // Do required initialization
        //message = "Hello World";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        // response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println( getCachedContent());
    }

    public void destroy() {
        // do nothing.
    }

    private String getCachedContent() throws IOException{
        if (cacheLastChanged == null
                || getDateDiff(cacheLastChanged,new Date(),TimeUnit.DAYS) > 1){
            cachedContent =  getNeedContent();
            cacheLastChanged = new Date();
        }

        System.out.println("cachedContent");
        return cachedContent;
    }


    private String getNeedContent() throws IOException{

        System.out.println("read content");

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

        httpClient.close();
        return res.toString();
    }

    public long getDateDiff(Date oldDate, Date newDate, TimeUnit timeUnit) {
        long diffInMillies = newDate.getTime() - oldDate.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

}
