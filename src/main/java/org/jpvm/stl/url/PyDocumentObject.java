package org.jpvm.stl.url;

import org.jpvm.objects.*;
import org.jsoup.nodes.Document;

public class PyDocumentObject extends PyObject {

  private Document document;

  public PyDocumentObject(Document docu) {
    this.document = docu;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(document.toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(document.toString());
  }
}
