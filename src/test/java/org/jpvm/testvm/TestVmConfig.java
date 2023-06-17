package org.jpvm.testvm;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

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
