package site.jaeu95.table_book.config.filter;

import lombok.RequiredArgsConstructor;
import site.jaeu95.table_book.config.JwtAuthenticationProvider;
import site.jaeu95.table_book.domain.common.UserVo;
import site.jaeu95.table_book.service.ManagerService;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 점장이 요청을 보내기 전 토큰을 통해서 인증하기 위한 Filter 입니다.
 */
@WebFilter(urlPatterns = "/manager/**")
@RequiredArgsConstructor
public class ManagerFilter implements Filter {
    private final JwtAuthenticationProvider provider;
    private final ManagerService managerService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        if (!provider.validateToken(token)) {
            throw new ServletException("Invalid Access");
        }
        UserVo userVo = provider.getUserVo(token);
        managerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new ServletException("Invalid Access"));

        chain.doFilter(request, response);
    }
}
