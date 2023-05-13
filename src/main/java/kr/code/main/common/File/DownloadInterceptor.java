package kr.code.main.common.File;

import kr.code.main.common.File.service.DownloadService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@NoArgsConstructor
public class DownloadInterceptor implements HandlerInterceptor {

    @Autowired
    private DownloadService downloadService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/istatic/")) {
            String fileId = requestUri.substring("/download/".length());
            downloadService.incrementDownloadCount(fileId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/istatic/")) {
            String fileId = requestUri.substring("/download/".length());
            String filename = getFilenameFromContentDisposition(response.getHeader(HttpHeaders.CONTENT_DISPOSITION));
            String ipAddress = request.getRemoteAddr();
            String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
            String logMessage = String.format("File '%s' (ID: %s) was downloaded by IP address %s using %s", filename, fileId, ipAddress, userAgent);
            System.out.println(logMessage);
        }
    }

    private String getFilenameFromContentDisposition(String contentDisposition) {
        if (contentDisposition != null) {
            String[] parts = contentDisposition.split(";");
            for (String part : parts) {
                if (part.trim().startsWith("filename=")) {
                    return part.substring("filename=".length()).trim();
                }
            }
        }
        return null;
    }
}
