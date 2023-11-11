package org.jpvm.stl.shutil;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.python.BuiltIn;

public class PyModuleMain extends PyModuleObject {

  public PyModuleMain(PyUnicodeObject moduleName) {
    super(moduleName);
  }

  public void copyFile(Path source, Path destination) throws IOException {
    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
  }

  public void copyDirectory(Path sourceDir, Path destinationDir) throws IOException {
    Files.walkFileTree(
        sourceDir,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Path targetFile = destinationDir.resolve(sourceDir.relativize(file));
            Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
              throws IOException {
            Path targetDir = destinationDir.resolve(sourceDir.relativize(dir));
            Files.createDirectories(targetDir);
            return FileVisitResult.CONTINUE;
          }
        });
  }

  @PyClassMethod
  public PyObject copytree(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 2) {
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "copytree() takes two arguments");
    }
    Path source = Paths.get(args.get(0).toString());
    if (!Files.isDirectory(source))
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.FileNotFoundError, "Directory " + source + " not found");
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
    if (!Files.exists(source)) {
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.FileNotFoundError, "File " + source + " not found");
    }
    if (Files.isDirectory(destination)) {
      destination = destination.resolve(source.getFileName());
    }
    try {
      copyFile(source, destination);
    } catch (IOException e) {
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
    }
    return BuiltIn.None;
  }
}
