package org.jpvm.stl.shutil;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.python.BuiltIn;

import java.io.IOException;
import java.nio.file.*;

public class PyModuleMain extends PyModuleObject {

  public void copyFile(Path source, Path destination) throws IOException {
    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
  }

  public void copyDirectory(Path source, Path destination) throws IOException, IOException {
    Files.createDirectories(destination);
    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(source)) {
      for (Path entry : directoryStream) {
        Path targetEntry = destination.resolve(entry.getFileName());
        if (Files.isDirectory(entry)) {
          copyDirectory(entry, targetEntry);
        } else {
          copyFile(entry, targetEntry);
        }
      }
    }
  }

  @PyClassMethod
  public PyObject copytree(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 2) {
      throw new RuntimeException("copytree() takes two arguments");
    }
    Path source = Paths.get(args.get(0).toString());
    Path destination = Paths.get(args.get(1).toString());
    try {
      copyDirectory(source, destination);
    } catch (IOException e) {
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
    }
    return BuiltIn.None;
  }

  @PyClassMethod
  public PyObject copyfile(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 2) {
      throw new RuntimeException("copytree() takes two arguments");
    }
    Path source = Paths.get(args.get(0).toString());
    Path destination = Paths.get(args.get(1).toString());
    try {
      copyFile(source, destination);
    } catch (IOException e) {
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
    }
    return BuiltIn.None;
  }

  public PyModuleMain(PyUnicodeObject moduleName) {
    super(moduleName);
  }
}
