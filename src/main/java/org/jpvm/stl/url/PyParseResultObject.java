package org.jpvm.stl.url;

import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.annotation.PyClassAttribute;

public class PyParseResultObject extends PyObject {
  @PyClassAttribute
  private final PyUnicodeObject protocol;
  @PyClassAttribute
  private final PyUnicodeObject host;
  @PyClassAttribute
  private final PyLongObject port;
  @PyClassAttribute
  private final PyUnicodeObject path;
  @PyClassAttribute
  private final PyUnicodeObject query;
  @PyClassAttribute
  private final PyUnicodeObject fragment;

  public PyParseResultObject(String protocol, String host, int port, String path, String query,
      String fragment) {
    this.protocol = new PyUnicodeObject(protocol);
    this.host = new PyUnicodeObject(host);
    this.port = PyLongObject.getLongObject(port);
    this.path = new PyUnicodeObject(path);
    this.query = new PyUnicodeObject(query);
    this.fragment = new PyUnicodeObject(fragment);
  }

  @Override
  public PyUnicodeObject str() {

    return new PyUnicodeObject(
        "ParseResult(protocol='" + protocol + "', host='" + host + "', port='" + port + "', path='"
            + path + "', query='" + query + "', fragment='" + fragment + "')");
  }
}
