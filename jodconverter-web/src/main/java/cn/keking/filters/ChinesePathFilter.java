package cn.keking.filters;

import cn.keking.utils.CheckIPUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yudian-it
 * @date 2017/11/30
 */
@Slf4j
public class ChinesePathFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        StringBuilder pathBuilder = new StringBuilder();
//        pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
//                .append(request.getServerPort()).append(((HttpServletRequest) request).getContextPath()).append("/");
//        request.setAttribute("baseUrl", pathBuilder.toString());
//        chain.doFilter(request, response);

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        StringBuilder pathBuilder = new StringBuilder();
        boolean isIp = CheckIPUtil.isIP(request.getServerName());
        boolean isInternalIp = CheckIPUtil.internalIp(request.getServerName());
        if (!isIp) {
            //域名
            pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append("/documentService/");

        } else if (!isInternalIp) {
            //公网ip
            pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                    .append(request.getServerPort()).append("/documentService/");
        } else {
            //内网ip
            pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                    .append(request.getServerPort()).append(((HttpServletRequest) request).getContextPath()).append("/");
        }
        request.setAttribute("baseUrl", pathBuilder.toString());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
