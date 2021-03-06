/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package net.nuttle.avro.bo;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface TestAvro {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"TestAvro\",\"namespace\":\"net.nuttle.avro.bo\",\"types\":[{\"type\":\"record\",\"name\":\"TestRecord\",\"fields\":[{\"name\":\"id\",\"type\":\"int\",\"aliases\":[\"oldid\"]}],\"aliases\":[\"net.nuttle.avro.OldTestRecord\"]},{\"type\":\"record\",\"name\":\"OldTestRecord\",\"fields\":[{\"name\":\"idold\",\"type\":\"int\"}]}],\"messages\":{\"upload\":{\"request\":[{\"name\":\"r\",\"type\":\"TestRecord\"}],\"response\":\"null\",\"one-way\":true}}}");
  void upload(net.nuttle.avro.bo.TestRecord r);

  @SuppressWarnings("all")
  public interface Callback extends TestAvro {
    public static final org.apache.avro.Protocol PROTOCOL = net.nuttle.avro.bo.TestAvro.PROTOCOL;
  }
}