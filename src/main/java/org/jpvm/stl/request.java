package org.jpvm.stl;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class request extends PyModuleObject {

    @PyClassAttribute
    private PyObject full_url;
    private Document document;

    public request(PyUnicodeObject moduleName) {
        super(moduleName);
    }

    @PyClassMethod
    public request urlopen(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        if(args.size() == 1){
            var val = args.get(0);
            if(val instanceof PyUnicodeObject o){
                try {
                    Document docu = Jsoup.connect(o.toString()).get();
                    this.document = docu;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return this;
    }

    @PyClassMethod
    public PyUnicodeObject title(PyTupleObject args, PyDictObject kwArgs){
        String title = document.title();
        return new PyUnicodeObject(title);
    }
}
