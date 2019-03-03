package net.nuttle.avro.bo.datafeed;

public class DatafeedSchemaHelper {

  public static AvroDatafeedSchemaField buildSchemaField(String name, String type) {
    AvroDatafeedSchemaField field = new AvroDatafeedSchemaField();
    field.setName(name);
    field.setDataType(type);
    return field;
  }
}
