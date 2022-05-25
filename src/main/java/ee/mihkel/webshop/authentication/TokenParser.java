package ee.mihkel.webshop.authentication;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class TokenParser extends BasicAuthenticationFilter {
    // @Value ei saa
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public TokenParser(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // Basic - username + password
    // Bearer - token, mille sees on mitmeid väärtusi

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            log.info(token);
            token = token.replace("Bearer ", "");

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();
                String issuer = claims.getIssuer();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        null
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (ExpiredJwtException |
                    MalformedJwtException |
                    UnsupportedJwtException |
                    SignatureException |
                    IllegalArgumentException e) {
                log.error(e.getMessage());
            }
        }

        super.doFilterInternal(request, response, chain);
    }
}
