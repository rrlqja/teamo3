package song.teamo3.security.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;
import song.teamo3.security.utils.jwt.JwtUtils;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = JwtUtils.createJwt(userDetails.getUsername(), userDetails.getAuthorities());
        String refreshToken = JwtUtils.createRefreshToken(userDetails.getUsername(), userDetails.getAuthorities());

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/authenticate/refresh")
                .sameSite("None")
                .build();

        response.setContentType("application/json");
        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        response.getWriter().write(objectMapper.writeValueAsString(tokens));
    }
}
