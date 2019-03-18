package threee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import threee.User.Users;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private final threee.security.JWTUserDetailsService jwtUserDetailsService;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                threee.security.JWTUserDetailsService jwtUserDetailsService) {
    super(authenticationManager);
    this.jwtUserDetailsService = jwtUserDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader(threee.security.SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(threee.security.SecurityConstants.TOKEN_PREFIX)) {
      response.sendError(401, "Unauthorized");
    } else {
      UsernamePasswordAuthenticationToken userPasswordAuth = getAuthenticationToken(request);
      SecurityContextHolder.getContext().setAuthentication(userPasswordAuth);
      chain.doFilter(request, response);
    }
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request){
    String token= request.getHeader(threee.security.SecurityConstants.HEADER_STRING);
    if (token == null){
      return null;
    } else {
      Claims body= Jwts.parser().setSigningKey(threee.security.SecurityConstants.SECRET)
          .parseClaimsJws(token.replace(threee.security.SecurityConstants.TOKEN_PREFIX, ""))
          .getBody();

      Users user=new Users();
      user.setEmail(body.getSubject());

      List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(
          body.get("role").toString().substring(1,body.get("role").toString().length()-1));

      threee.security.JWTUserDetails userDetails = new threee.security.JWTUserDetails(
          user.getEmail(),
          user.getPassword(),
          grantedAuthorities);

      if (user.getEmail() != null) {
        return new UsernamePasswordAuthenticationToken(
            user, null,
            userDetails.getAuthorities());
      } else {
        return null;
      }
    }
  }
}
