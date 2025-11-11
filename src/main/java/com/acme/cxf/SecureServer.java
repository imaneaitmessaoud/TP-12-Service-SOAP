package com.acme.cxf;

import com.acme.cxf.impl.HelloServiceImpl;
import com.acme.cxf.security.UTPasswordCallback;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import java.util.HashMap;
import java.util.Map;

public class SecureServer {
  public static void main(String[] args) {
    try {
      System.out.println("Starting SecureServer...");

      Map<String,Object> inProps = new HashMap<>();
      inProps.put("action", "UsernameToken");
      inProps.put("passwordType", "PasswordText");
      inProps.put("passwordCallbackRef", new UTPasswordCallback(Map.of("student","secret123")));

      WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);

      JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();

      // Enregistrer l'instance implémentation (recommandé)
      HelloServiceImpl impl = new HelloServiceImpl();
      factory.setServiceBean(impl);

      // Utiliser la LoggingFeature (remplace LoggingInInterceptor)
      factory.getFeatures().add(new LoggingFeature());

      // Ajouter l'intercepteur WSS4J AVANT create()
      factory.getInInterceptors().add(wssIn);

      factory.setAddress("http://localhost:8080/services/hello-secure");

      // Créer le serveur et conserver la référence
      Server server = factory.create();

      System.out.println("Secure server created.");
      System.out.println("Secure WSDL: http://localhost:8080/services/hello-secure?wsdl");
      System.out.println("Published address: " + server.getEndpoint().getEndpointInfo().getAddress());

    } catch (Exception e) {
      System.err.println("Failed to start SecureServer:");
      e.printStackTrace();
    }
  }
}
