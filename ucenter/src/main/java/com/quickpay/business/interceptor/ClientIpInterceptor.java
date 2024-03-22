package com.quickpay.business.interceptor;
import com.quickpay.commons.context.ContextKey;
import com.quickpay.commons.context.ThreadContext;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class ClientIpInterceptor implements HandlerInterceptor {
    private static final String unknownIP = "unknown";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("pre handle");
        String clientIP = getClientIP(request);
        //todo throw exception if ip from blocked list

        ThreadContext.set(ContextKey.CLIENT_IP.name(), clientIP);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       log.info("post handle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      log.info("after completion");
      // Need to clean up to avoid reuse data disorder
       ThreadContext.close();
    }

    public String getClientIP(HttpServletRequest request){
        String ip = "";
        for(String header : HEADERS_TO_TRY){
            ip = request.getHeader(header);
            if(StringUtils.isNotBlank(ip) && !ip.equalsIgnoreCase(unknownIP)){
                break;
            }
        }
        //If cannot find valid IP use remoteAddress
        if(StringUtils.isBlank(ip) || ip.equalsIgnoreCase(unknownIP)){
            ip = request.getRemoteAddr();
        }
        //If try many times ip can be list use the first child
        if(StringUtils.isNotBlank(ip) && ip.indexOf(",") > 0){
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        return ip;
    }
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};
}
