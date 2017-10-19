package org.apache.ambari.server.topology.validators;

import java.util.HashMap;
import java.util.Map;

import org.apache.ambari.server.topology.BlueprintImplV2;
import org.apache.ambari.server.topology.HostGroupV2;
import org.apache.ambari.server.topology.HostGroupV2Impl;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class BlueprintImplV2Test {

  static String BLUEPRINT_V2_JSON;

  @BeforeClass
  public static void setUpClass() throws Exception {
    BLUEPRINT_V2_JSON = Resources.toString(Resources.getResource("blueprintv2/blueprintv2.json"), Charsets.UTF_8);
  }

  @Test
  public void testSerialization() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());
    SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
    resolver.addMapping(HostGroupV2.class, HostGroupV2Impl.class);
    module.setAbstractTypes(resolver);
    mapper.registerModule(module);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    BlueprintImplV2 bp = mapper.readValue(BLUEPRINT_V2_JSON, BlueprintImplV2.class);
    String bpJson = mapper.writeValueAsString(bp);
    System.out.println(bpJson);
    System.out.println("\n\n====================================================================================\n\n");
    Map<String, Object> map = mapper.readValue(BLUEPRINT_V2_JSON, HashMap.class);
    System.out.println(map);
    System.out.println("\n\n====================================================================================\n\n");
    String bpJson2 = mapper.writeValueAsString(map);
    System.out.println(bpJson2);
    System.out.println("\n\n====================================================================================\n\n");
    BlueprintImplV2 bp2 = mapper.readValue(bpJson2, BlueprintImplV2.class);
    System.out.println(bp2);
  }

}