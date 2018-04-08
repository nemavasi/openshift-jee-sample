<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>First JSP</title>
</head>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.http.*" %>
<%@ page import="org.apache.http.client.methods.*" %>
<%@ page import="org.apache.http.client.entity.*" %>
<%@ page import="org.apache.http.impl.client.*" %>

<body>

<%
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

%>

<%=content%>


</body>
</html>