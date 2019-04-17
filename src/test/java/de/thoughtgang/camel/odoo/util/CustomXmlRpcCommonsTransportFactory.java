package de.thoughtgang.camel.odoo.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcCommonsTransport;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.XmlRpcTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomXmlRpcCommonsTransportFactory extends
    XmlRpcCommonsTransportFactory {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  public CustomXmlRpcCommonsTransportFactory(XmlRpcClient pClient) {
    super(pClient);
  }
  
  @Override
  public XmlRpcTransport getTransport() {
    return new LoggingTransport(this);
  }
  
  private class LoggingTransport extends XmlRpcCommonsTransport {

    public LoggingTransport(CustomXmlRpcCommonsTransportFactory pFactory) {
      super(pFactory);
    }

    /**
     * Logs the request content in addition to the actual work.
     */
    @Override
    protected void writeRequest(final ReqWriter pWriter) throws XmlRpcException {
      super.writeRequest(pWriter);
      if (logger.isDebugEnabled()) {
        Util.logRequest(logger, method.getRequestEntity());
      }
    }

    /**
     * Logs the response from the server, and returns the contents of
     * the response as a ByteArrayInputStream.
     */
    @Override
    protected InputStream getInputStream() throws XmlRpcException {
      InputStream istream = super.getInputStream();
      if (logger.isDebugEnabled()) {
        return new ByteArrayInputStream(
          Util.logResponse(logger, istream).getBytes());
      } else {
        return istream;
      }
    }
  }
}