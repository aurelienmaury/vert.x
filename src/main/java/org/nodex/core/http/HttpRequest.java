package org.nodex.core.http;

import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.nodex.core.buffer.Buffer;
import org.nodex.core.buffer.DataHandler;

import java.util.List;
import java.util.Map;

/**
 * User: timfox
 * Date: 25/06/2011
 * Time: 19:19
 */
public class HttpRequest {
  public final String method;
  public final String uri;
  public final Map<String, String> headers;
  private Map<String, List<String>> params;

  protected HttpRequest(String method, String uri, Map<String, String> headers) {
    this.method = method;
    this.uri = uri;
    this.headers = headers;
  }

  private DataHandler dataHandler;

  public void data(DataHandler dataHandler) {
    this.dataHandler = dataHandler;
  }

  public String getParam(String param) {
    if (params == null) {
      QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
      params = queryStringDecoder.getParameters();
    }
    List<String> list = params.get(param);
    if (list != null) {
      return list.get(0);
    } else {
      return null;
    }
  }

  void dataReceived(Buffer data) {
    if (dataHandler != null) {
      dataHandler.onData(data);
    }
  }

}