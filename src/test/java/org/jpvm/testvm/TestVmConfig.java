package org.jpvm.testvm;

import java.util.Map;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class TestVmConfig {

  @Test
  public void testConfig() {
    Yaml yaml = new Yaml();
    var load = yaml.loadAs(this.getClass().getResourceAsStream("/jpvm-config.yml"),
        Map.class);
    System.out.println(load);
    System.out.println(load.get("vm-interval"));
  }
}
