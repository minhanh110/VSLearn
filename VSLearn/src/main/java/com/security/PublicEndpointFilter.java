package com.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.HandlerExecutionChain;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PublicEndpointFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(PublicEndpointFilter.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        logger.debug("Processing request: {}", requestURI);

        try {
            // Get the best matching handler
            HandlerExecutionChain handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);
            logger.debug("Handler execution chain: {}", handlerExecutionChain);

            if (handlerExecutionChain != null && handlerExecutionChain.getHandler() instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();
                logger.debug("Handler method: {}", handlerMethod);
                logger.debug("Handler method class: {}", handlerMethod.getBeanType().getName());
                logger.debug("Handler method name: {}", handlerMethod.getMethod().getName());
                
                // Check for @PublicEndpoint annotation on the method or class
                boolean isPublic = handlerMethod.getMethodAnnotation(PublicEndpoint.class) != null ||
                                 handlerMethod.getBeanType().getAnnotation(PublicEndpoint.class) != null;
                
                logger.debug("Endpoint {} is public: {}", requestURI, isPublic);

                if (isPublic) {
                    logger.debug("Allowing public access to: {}", requestURI);
                    chain.doFilter(request, response);
                    return;
                }
            } else {
                logger.debug("No handler method found for: {}", requestURI);
                if (handlerExecutionChain != null) {
                    logger.debug("Handler type: {}", handlerExecutionChain.getHandler().getClass().getName());
                }
            }
        } catch (Exception e) {
            logger.error("Error processing request: {}", e.getMessage(), e);
        }

        // If we reach here, either the endpoint is not public or there was an error
        logger.debug("Proceeding with normal authentication for: {}", requestURI);
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldNotFilter = path.startsWith("/v2/api-docs") ||
                                path.startsWith("/swagger-resources") ||
                                path.startsWith("/swagger-ui.html") ||
                                path.startsWith("/configuration") ||
                                path.startsWith("/webjars") ||
                                path.startsWith("/public");
        
        logger.debug("Should not filter path {}: {}", path, shouldNotFilter);
        return shouldNotFilter;
    }
} 