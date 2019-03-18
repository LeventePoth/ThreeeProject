package threee.User;

import com.sun.jersey.api.client.ClientResponse;

public interface MailgunService {
  ClientResponse sendSimpleMessage(threee.User.Users users);
  ClientResponse sendComplexMessage(threee.User.Users users);
}
