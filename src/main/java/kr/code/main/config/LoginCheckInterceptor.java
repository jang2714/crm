package kr.code.main.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        boolean isPassed = true;
        if (request.getSession().getAttribute("user") == null) {

            if(isAjaxRequest(request)) {
                response.sendError(401);
            } else {
                response.sendRedirect("/404");
            }

            isPassed = false;
        }

        return isPassed;	// false가 되어야만 redirect가 시작된다.
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("AJAX");

        return header != null && header.equals("true");
    }
}
