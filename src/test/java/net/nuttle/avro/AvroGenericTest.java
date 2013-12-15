package net.nuttle.avro;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import net.nuttle.avro.bo.TestRecord;

import static org.junit.Assert.assertEquals;

/*
 * Based on code from:
 * https://sites.google.com/site/developertips/Home/java/apache-avro
 * 
 * This is an example of a "generic" writer and reader, so that you don't have to have defined classes.
 */

public class AvroGenericTest {
  
  private static final Logger LOG = Logger.getLogger(AvroGenericTest.class);

  @Test
  public void testGenericFromString() throws IOException {
    // Schema
    String schemaDescription = " {    \n"
        + " \"name\": \"FacebookUser\", \n"
        + " \"type\": \"record\",\n" + " \"fields\": [\n"
        + "   {\"name\": \"name\", \"type\": \"string\"},\n"
        + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
        + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
        + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

    //Schema s = Schema.parse(schemaDescription); //Deprecated, use Schema.Parser instead
    Schema.Parser parser = new Schema.Parser();
    Schema s = parser.parse(schemaDescription);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);
    GenericDatumWriter<GenericRecord> w = new GenericDatumWriter<GenericRecord>(s);

    // Populate data
    GenericRecord r = new GenericData.Record(s);
    r.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
    r.put("num_likes", 1);
    r.put("num_groups", 50101);
    r.put("num_photos", 12);

    // Encode
    w.write(r, e);
    e.flush();

    byte[] encodedByteArray = outputStream.toByteArray();
    String encodedString = outputStream.toString("UTF-8");

    LOG.debug("encodedString: "+encodedString);

    // Decode using same schema
    DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(s);
    Decoder decoder = DecoderFactory.get().binaryDecoder(encodedByteArray, null);
    GenericRecord result = reader.read(null, decoder);
    assertEquals("Doctor Who", result.get("name").toString());
    assertEquals(1, Integer.parseInt(result.get("num_likes").toString()));
    assertEquals(50101, Integer.parseInt(result.get("num_groups").toString()));
    assertEquals(12, Integer.parseInt(result.get("num_photos").toString()));

  }
 
  /**
   * This demonstrates that you can use generic readers and writers with specific data types,
   * though it's not clear that it would ever make sense to do so.
   * Although the TestRecord.SCHEMA$ is equivalent to the text in an avsc file,
   * so it demonstrates that a server could have just the avsc file (but not the generated classes), 
   * and still read the data and use the generic get method.
   * @throws IOException
   */
  @Test
  public void testGenericFromString2() throws IOException {
    Schema s = TestRecord.SCHEMA$;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);
    GenericDatumWriter<TestRecord> w = new GenericDatumWriter<TestRecord>(s);
    /*
    GenericRecord r = new GenericData.Record(s);
    r.put("id", new org.apache.avro.util.Utf8("11"));
    */
    TestRecord r = new TestRecord();
    r.setId(15);
    
    w.write(r, e);
    e.flush();
  
    byte[] encodedByteArray = outputStream.toByteArray();
    String encodedString = outputStream.toString("UTF-8");

    System.out.println("encodedString: "+encodedString);

    // Decode using same schema
    DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(s);
    Decoder decoder = DecoderFactory.get().binaryDecoder(encodedByteArray, null);
    GenericRecord result = reader.read(null, decoder);
    assertEquals(15, Integer.parseInt(result.get("id").toString()));

  }
}
