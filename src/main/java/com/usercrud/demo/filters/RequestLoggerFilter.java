package com.usercrud.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class RequestLoggerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CustomResponseWrapper responseWrapper = new CustomResponseWrapper((HttpServletResponse) servletResponse);
        CustomRequestWrapper requestWrapper = new CustomRequestWrapper((HttpServletRequest) servletRequest);

        // Log request details
        log.info("Request Path: " + requestWrapper.getRequestURI());
        log.info("HTTP method: " + requestWrapper.getMethod());
        log.info("Remote Address: " + requestWrapper.getRemoteAddr());

        String requestBody = requestWrapper.getBody();
        log.info("Request Body: " + requestBody);


        // Proceed with the request
        FilterChain chain = filterChain;
        chain.doFilter(requestWrapper, responseWrapper);

        // Log response details
        String responseBody = responseWrapper.getContent();
        log.info("Response Body: " + responseBody);
        log.info("Response Status: " + responseWrapper.getStatus());

        // Write the captured response back to the client
        servletResponse.getOutputStream().write(responseBody.getBytes());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public class CustomResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final PrintWriter writer = new PrintWriter(outputStream);

        public CustomResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                }

                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }
            };
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        public String getContent() {
            writer.flush(); // Ensure all content is written
            return outputStream.toString();
        }
    }

    public class CustomRequestWrapper extends HttpServletRequestWrapper {
        private final byte[] requestBody;

        public CustomRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            this.requestBody = readBody(request.getInputStream());
        }

        private byte[] readBody(InputStream inputStream) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }

        public String getBody() {
            return new String(requestBody, StandardCharsets.UTF_8);
        }

        @Override
        public ServletInputStream getInputStream() {
            return new ServletInputStream() {
                private final ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody);

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() {
                    return inputStream.read();
                }
            };
        }

    }
}


