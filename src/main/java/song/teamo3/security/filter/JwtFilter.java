package song.teamo3.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import song.teamo3.domain.common.exception.security.exceptions.InvalidJwtException;
import song.teamo3.security.authentication.userdetails.service.UserDetailsServiceImpl;
import song.teamo3.security.utils.jwt.JwtUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        // 테스트용
        if (authorization != null || authorization.startsWith("Bearer a")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorization.substring(7);
            String username = JwtUtils.validateJwt(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);
        } catch (InvalidJwtException e) {
            doResponse(response, HttpServletResponse.SC_BAD_REQUEST, "토큰이 만료되었습니다.");
            return;
        } catch (JwtException e) {
            doResponse(response, HttpServletResponse.SC_BAD_REQUEST, "jwt exception");
            return;
        } catch (Exception e) {
            doResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void doResponse(HttpServletResponse response, int status, String message) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, String> messages = new HashMap<>();
        messages.put("message", message);

        response.getWriter().write(objectMapper.writeValueAsString(messages));
    }
}
