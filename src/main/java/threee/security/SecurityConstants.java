package threee.security;

public class SecurityConstants {
  public static final String SECRET = System.getenv("JWT_SECRET");
  public static final String TOKEN_PREFIX = System.getenv("TOKEN_PREFIX");
  public static final String HEADER_STRING = System.getenv("HEADER_STRING");
  public static final long EXPIRATION_TIME = 900_000L;
}
