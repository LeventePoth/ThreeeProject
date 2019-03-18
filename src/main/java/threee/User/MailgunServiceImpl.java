package threee.User;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.io.File;

@Service
public class MailgunServiceImpl implements threee.User.MailgunService {

  String mailgunApiKey = "3183894f7729b7ca01a841a642dd04a5-f45b080f-1f1a9da7";
  String mailgunDomainName = "sandbox2146b2b3021740bc9ab507b4b6dde2eb.mailgun.org";

  @Override
  public ClientResponse sendSimpleMessage(threee.User.Users users) {

    //String recipient = "nemethlilla91@gmail.com";
    String recipient = users.getEmail();
    Client client = Client.create();
    client.addFilter(new HTTPBasicAuthFilter("api", mailgunApiKey));
    WebResource webResource = client.resource("https://api.mailgun.net/v3/" + mailgunDomainName
        + "/messages");
    MultivaluedMapImpl formData = new MultivaluedMapImpl();
    formData.add("from", "Devma <emeralddevma@" + mailgunDomainName + ">");
    formData.add("to", recipient);
    formData.add("subject", "regisztrációs email");
    formData.add("text", "Üdv!! :)");
    return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,
        formData);
  }

  public ClientResponse sendComplexMessage(threee.User.Users users) {
    Client client = Client.create();
    String recipient = users.getEmail();
    client.addFilter(new HTTPBasicAuthFilter("api", mailgunApiKey));
    WebResource webResource = client.resource("https://api.mailgun.net/v3/" + mailgunDomainName
        + "/messages");
    FormDataMultiPart formData = new FormDataMultiPart();
    formData.field("from", "Devma <emeralddevma@" + mailgunDomainName + ">");
    formData.field("to", recipient);
    formData.field("subject", "Complex Mailgun Example");
    formData.field("html", "email.html");
    ClassLoader classLoader = getClass().getClassLoader();
    File txtFile = new File(classLoader.getResource("example-attachment.txt").getFile());
    formData.bodyPart(new FileDataBodyPart("attachment", txtFile, MediaType.TEXT_PLAIN_TYPE));
    return webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE)
        .post(ClientResponse.class, formData);
  }
}
