package com.acme.cxf.client;

import com.acme.cxf.api.HelloService;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import java.net.URI;
import java.net.URL;

public class ClientDemo {
  public static void main(String[] args) throws Exception {
     URI uri = URI.create("http://localhost:8080/services/hello?wsdl");
     URL wsdl = uri.toURL();
    QName qname = new QName("http://api.cxf.acme.com/", "HelloService");
    Service svc = Service.create(wsdl, qname);
    HelloService port = svc.getPort(HelloService.class);

    System.out.println(port.sayHello("ClientJava"));
    System.out.println(port.findPersonById("P-777").getName());
  }
}