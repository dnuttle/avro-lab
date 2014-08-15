package net.nuttle.avro;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;

public final class JsonSchemas {

  public static final String USER_SCHEMA_STRING = "{"
      + "\"namespace\":\"net.nuttle.avro.generic\","
      + "\"type\":\"record\","
      + "\"name\":\"User\","
      + "\"fields\":["
      + "{\"name\":\"id\", \"type\":\"string\"},"
      + "{\"name\":\"name\", \"type\":\"string\"},"
      + "{\"name\":\"address\",\"type\":\"Address\"}"
      + "]}";

  public static final String ADDRESS_SCHEMA_STRING = "{"
      + "\"namespace\":\"net.nuttle.avro.generic\","
      + "\"type\":\"record\","
      + "\"name\":\"Address\","
      + "\"fields\":["
      + "{\"name\":\"city\", \"type\":\"string\"},"
      + "{\"name\":\"state\", \"type\":\"string\"}"
      + "]}";
  public static final String FACEBOOK_SCHEMA_STRING = " {    \n"
      + " \"name\": \"FacebookUser\", \n"
      + " \"type\": \"record\",\n" + " \"fields\": [\n"
      + "   {\"name\": \"name\", \"type\": \"string\"},\n"
      + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
      + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
      + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";
  
  public static Schema USER_SCHEMA;
  public static Schema ADDRESS_SCHEMA;
  public static Schema FACEBOOK_SCHEMA;
  
  static {
    Parser parser = new Parser();
    ADDRESS_SCHEMA = parser.parse(ADDRESS_SCHEMA_STRING);
    USER_SCHEMA = parser.parse(USER_SCHEMA_STRING);
    FACEBOOK_SCHEMA = parser.parse(FACEBOOK_SCHEMA_STRING);
  }
}