package org.jpvm.stl.url;

import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyModuleObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.annotation.PyClassAttribute;

public class PyParseResultObject extends PyObject {
  @PyClassAttribute private PyUnicodeObject protocol;
  @PyClassAttribute private PyUnicodeObject host;
  @PyClassAttribute private PyLongObject port;
  @PyClassAttribute private PyUnicodeObject path;
  @PyClassAttribute private PyUnicodeObject query;
  @PyClassAttribute private PyUnicodeObject fragment;

  public PyParseResultObject(
      String protocol, String host, int port, String path, String query, String fragment) {
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
        new String(
            "ParseResult(protocol='"
                + protocol
                + "', host='"
                + host
                + "', port='"
                + port
                + "', path='"
                + path
                + "', query='"
                + query
                + "', fragment='"
                + fragment
                + "')"));
  }
}
