package com.acme.cxf.security;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import javax.security.auth.callback.*;
import java.io.IOException;
import java.util.Map;

public class UTPasswordCallback implements CallbackHandler {
  private final Map<String,String> users;
  public UTPasswordCallback(Map<String,String> users) { this.users = users; }

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (Callback cb : callbacks) {
      if (cb instanceof WSPasswordCallback) {
        WSPasswordCallback pc = (WSPasswordCallback) cb;
        System.out.println("UTPasswordCallback: identifier=" + pc.getIdentifier() + ", usage=" + pc.getUsage());
        String pass = users.get(pc.getIdentifier());
        if (pass != null) {
          System.out.println("UTPasswordCallback: setting password for " + pc.getIdentifier());
          pc.setPassword(pass);
        } else {
          System.out.println("UTPasswordCallback: no password found for " + pc.getIdentifier());
        }
      } else {
        System.out.println("UTPasswordCallback: unexpected callback type: " + cb.getClass().getName());
      }
    }
  }
}
