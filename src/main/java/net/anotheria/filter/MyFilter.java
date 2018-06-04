package net.anotheria.filter;

import net.anotheria.rproxy.PageContent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class MyFilter implements Filter {

    private static Map<String, HttpEntity> s = null;

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        System.out.println("Filter!");


        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = ((HttpServletRequest) servletRequest);
            String url = request.getRequestURL().toString();

            //get page with new url
            String page = PageContent.getPageContent("https://www.google.com");
            //System.out.println(page);
            servletResponse.getWriter().print(page);
//            if (urlMustBeReplaced(url)) {
//                //get page with new url
//                String page = PageContent.getPageContent("https://www.google.com");
//                //System.out.println(page);
//                servletResponse.getWriter().print(page);
//            }
//            if (urlRelative(url)) {
//                HttpClient cl = HttpClientBuilder.create().build();
//                HttpGet re = new HttpGet(url);
//
//                HttpResponse response = cl.execute(re);
//                HttpEntity entity = response.getEntity();
//            }
            PageContent.proxy(url);
            //filterChain.doFilter(servletRequest, servletResponse);

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean urlRelative(String url) {
        if (!url.equals("http://localhost:8080/")) {
            System.out.println("Relative!");
            return true;
        }
        return false;

    }

    private boolean urlMustBeReplaced(String url) {
        return true;
    }


}