package org.jpvm.stl.url;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PyDocumentObject extends PyObject {

    private Document document;

    public PyDocumentObject(Document docu) {
        this.document = docu;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
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


