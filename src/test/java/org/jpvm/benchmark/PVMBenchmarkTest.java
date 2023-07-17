package org.jpvm.benchmark;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1)
@Measurement(iterations = 20)
@Fork(1)
public class PVMBenchmarkTest {

  @Benchmark
  public void testFib() throws PyException, IOException {
    String filename = "src/test/resources/benchmark/__pycache__/fib.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }
}
