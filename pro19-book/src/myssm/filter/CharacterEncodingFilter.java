package myssm.filter;

import myssm.util.StringUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/4 3:31 PM
 * @description
 */
public class CharacterEncodingFilter implements Filter {
    private final String ENCODING_KEY = "coding";
    private String defaultEncoding = "UTF-8";

    @Override
    public void init(FilterConfig config) throws ServletException {
        String encoding = config.getInitParameter(ENCODING_KEY);
        if(StringUtil.isNotEmpty(encoding)){
            defaultEncoding = encoding;
        }
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException{
        request.setCharacterEncoding(defaultEncoding);
        chain.doFilter(request,response);
    }
    @Override
    public void destroy(){}
}