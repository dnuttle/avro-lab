package net.nuttle.avro.json;

public final class JsonSchemas {

  public static final String USER_SCHEMA = "{"
      + "\"namespace\":\"net.nuttle.avro.generic\","
      + "\"type\":\"record\","
      + "\"name\":\"User\","
      + "\"fields\":["
      + "{\"name\":\"id\", \"type\":\"string\"},"
      + "{\"name\":\"name\", \"type\":\"string\"},"
      + "{\"name\":\"address\",\"type\":\"Address\"}"
      + "]}";

  public static final String ADDRESS_SCHEMA = "{"
      + "\"namespace\":\"net.nuttle.avro.generic\","
      + "\"type\":\"record\","
      + "\"name\":\"Address\","
      + "\"fields\":["
      + "{\"name\":\"city\", \"type\":\"string\"},"
      + "{\"name\":\"state\", \"type\":\"string\"}"
      + "]}";
}
