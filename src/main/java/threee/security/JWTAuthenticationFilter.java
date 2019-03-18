package threee.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import threee.User.Users;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static threee.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  private AuthenticationManager authenticationManager;
  
  public JWTAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
    super(defaultFilterProcessesUrl);
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    try {
      Users user = new ObjectMapper().readValue(
              request.getInputStream(),
             Users.class);
      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              user.getEmail(),
              user.getPassword()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {

    ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC)
        .plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

    Claims claims= Jwts.claims()
        .setSubject(((User) authResult.getPrincipal()).getUsername());
        claims.put("role", (((User) authResult.getPrincipal()).getAuthorities().toString()));
        String token= Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(expirationTimeUTC.toInstant()))
            .signWith(SignatureAlgorithm.HS256, SECRET)
            .compact();
        response.getWriter().write(token);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }
}
