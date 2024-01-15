package org.jpvm.stl.url;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PyModuleMain extends PyModuleObject {

  private PyParseResultObject parseResult;

  private PyDocumentObject document;

  public PyModuleMain(PyUnicodeObject moduleName) {
    super(moduleName);
  }

  @PyClassMethod
  public PyDocumentObject urlopen(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var val = args.get(0);
      if (val instanceof PyUnicodeObject o) {
        try {
          Document docu = Jsoup.connect(o.toString()).get();
          document = new PyDocumentObject(docu);
          return document;
        } catch (IOException e) {
          PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
          return null;
        }
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.AttributeError, " argument error");
    return null;
  }

  @PyClassMethod
  public PyObject urlretrieve(PyTupleObject args, PyDictObject kwArgs)
      throws PyException, IOException {
    if (args.size() == 2) {
      try {
        String fileUrl = args.get(0).toString();
        String savePath = args.get(1).toString();
        Connection.Response response = Jsoup.connect(fileUrl).ignoreContentType(true).execute();
        byte[] content = response.bodyAsBytes();
        FileOutputStream outputStream = new FileOutputStream(savePath);
        outputStream.write(content);
        outputStream.close();
        System.out.println("file downloaded successful!!!");
        return new PyUnicodeObject("1");
      } catch (PyException e) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getMessage());
        return null;
      } catch (IOException e) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
        return null;
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.AttributeError, "argument error");
    return null;
  }

  @PyClassMethod
  public PyObject urlparse(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      try {
        String urlString = args.get(0).toString();
        URL url = new URL(urlString);
        parseResult =
            new PyParseResultObject(
                url.getProtocol(),
                url.getHost(),
                url.getPort(),
                url.getPath(),
                url.getQuery(),
                url.getRef());

        return parseResult;
      } catch (PyException e) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getMessage());
        return null;
      } catch (MalformedURLException e) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getMessage());
        return null;
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.AttributeError, "attribute error");
    return null;
  }
}
