package com.lbsj.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class RequestLogFilter extends HttpFilter {


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        super.doFilter(request, response, chain);
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("requestURI:{}", requestURI);
        byte[] content = requestWrapper.getContentAsByteArray();
        int status = response.getStatus();
        log.info("response status:{}", status);
        responseWrapper.copyBodyToResponse();
    }
}
