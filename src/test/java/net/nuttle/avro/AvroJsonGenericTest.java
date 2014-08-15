package net.nuttle.avro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import net.nuttle.avro.json.JsonSchemas;

public class AvroJsonGenericTest {

  private static final String TEST_ID_NAME = "id";
  private static final String TEST_ID_VAL = "ABCDEF";
  private static final String TEST_NAME_NAME = "name";
  private static final String TEST_NAME_VAL = "John Doe";
  private static final String TEST_CITY_NAME = "city";
  private static final String TEST_CITY_VAL = "algonquin";
  private static final String TEST_STATE_NAME = "state";
  private static final String TEST_STATE_VAL = "illinois";
  private static final String TEST_ADDRESS_NAME = "address";
  private static final Logger LOG = Logger.getLogger(AvroJsonGenericTest.class);
  
  private static final Schema USER_SCHEMA;
  private static final Schema ADDRESS_SCHEMA;
  private static final Parser PARSER;
  private static GenericRecord user;
  private static GenericRecord address;
  
  static {
    PARSER = new Parser();
    ADDRESS_SCHEMA = PARSER.parse(JsonSchemas.ADDRESS_SCHEMA);
    USER_SCHEMA = PARSER.parse(JsonSchemas.USER_SCHEMA);
  }

  /**
   * Creates a file using a generic schema, writes one record,
   * reads it back, confirms values are correct,
   * deletes file.
   * This demonstrates that schemas can be entirely dynamic.
   * They are sent with the data, so there is no need for static
   * classes on the receiving end.
   * It is preferable and more efficient (and clearer) to have generated
   * static classes, but it is not required.
   */
  @Test
  public void testGenericSchemaToFile() {
    resetRecords();
    try {
      //Confirm that user schema has the expected fields
      List<String> userFieldNames = new ArrayList<String>();
      userFieldNames.add("name");
      userFieldNames.add("id");
      userFieldNames.add("address");

      List<Schema.Field> fields = USER_SCHEMA.getFields();
      Iterator<Schema.Field> fieldIter = fields.iterator();
      int fieldcount=0;
      while(fieldIter.hasNext()) {
        Schema.Field field = fieldIter.next();
        assertTrue(userFieldNames.contains(field.name()));
        fieldcount++;
      }
      assertEquals(userFieldNames.size(), fieldcount);
      
      //Write data file
      File file = new File("generic_test.avro");
      DatumWriter<GenericRecord> dw = new GenericDatumWriter<GenericRecord>();
      DataFileWriter<GenericRecord> fw = new DataFileWriter<GenericRecord>(dw);
      fw.create(USER_SCHEMA, file);
      fw.append(user);
      fw.close();

      //Read file back and check its fields
      DatumReader<GenericRecord> dr = new GenericDatumReader<GenericRecord>();
      DataFileReader<GenericRecord> fr = new DataFileReader<GenericRecord>(file, dr);
      int count = 0;
      while(fr.hasNext()) {
        user = fr.next();
        address = (GenericRecord) user.get(TEST_ADDRESS_NAME);
        count++;
        assertEquals(TEST_ID_VAL, user.get(TEST_ID_NAME).toString());
        assertEquals(TEST_NAME_VAL, user.get(TEST_NAME_NAME).toString());
        assertEquals(TEST_CITY_VAL, address.get(TEST_CITY_NAME).toString());
        assertEquals(TEST_STATE_VAL, address.get(TEST_STATE_NAME).toString());
      }
      assertEquals(1, count);
      fr.close();
      //Delete file
      assertTrue("Could not delete file after test", file.delete());
    } catch (Exception e) {
      LOG.error("Error:", e);
      fail("Unexpected " + e.getClass().getSimpleName());
    }
  }
  
  /**
   * Test writing a generic record to an output stream.
   * Key difference here is that an Encoder is used to write to the stream,
   * and a Decoder is used to read from it.
   */
  @Test
  public void testGenericSchemaToOutputStream() {
    resetRecords();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Encoder encoder = EncoderFactory.get().binaryEncoder(bos, null);
    DatumWriter<GenericRecord> dw = new GenericDatumWriter<GenericRecord>(USER_SCHEMA);
    DatumReader<GenericRecord> dr = new GenericDatumReader<GenericRecord>(USER_SCHEMA);
    try {
      dw.write(user, encoder);
      encoder.flush();
      BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bos.toByteArray(), null);
      user = dr.read(null, decoder);
      assertEquals(TEST_ID_VAL, user.get(TEST_ID_NAME).toString());
      assertEquals(TEST_NAME_VAL, user.get(TEST_NAME_NAME).toString());
      assertEquals(TEST_CITY_VAL, address.get(TEST_CITY_NAME).toString());
      assertEquals(TEST_STATE_VAL, address.get(TEST_STATE_NAME).toString());
    } catch (Exception e) {
      LOG.error("Error:", e);
      fail("Unexpected " + e.getClass().getSimpleName());
    }
  }

  /**
   * Test writing from a generic record to Json, read back as a string and as an InputStream
   */
  @Test
  public void testGenericSchemaToJson() {
    resetRecords();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    try {
      Encoder encoder = EncoderFactory.get().jsonEncoder(USER_SCHEMA, bos);
      DatumWriter<GenericRecord> dw = new GenericDatumWriter<GenericRecord>(USER_SCHEMA);
      DatumReader<GenericRecord> dr = new GenericDatumReader<GenericRecord>(USER_SCHEMA);
      dw.write(user, encoder);
      encoder.flush();
      
      //StringBufferInputStream ios = new StringBufferInputStream(bos.toString());
      Decoder decoder = DecoderFactory.get().jsonDecoder(USER_SCHEMA, bos.toString());
      user = dr.read(null,  decoder);
      assertEquals(TEST_ID_VAL, user.get(TEST_ID_NAME).toString());
      assertEquals(TEST_NAME_VAL, user.get(TEST_NAME_NAME).toString());
      assertEquals(TEST_CITY_VAL, address.get(TEST_CITY_NAME).toString());
      assertEquals(TEST_STATE_VAL, address.get(TEST_STATE_NAME).toString());
      
      //Now do the same but with InputStream
      resetRecords();
      encoder = EncoderFactory.get().jsonEncoder(USER_SCHEMA, bos);
      dw = new GenericDatumWriter<GenericRecord>(USER_SCHEMA);
      dr = new GenericDatumReader<GenericRecord>(USER_SCHEMA);
      dw.write(user, encoder);
      encoder.flush();
      InputStream ios = new ByteArrayInputStream(bos.toString().getBytes("UTF-8"));
      decoder = DecoderFactory.get().jsonDecoder(USER_SCHEMA, ios);
      user = dr.read(null,  decoder);
      assertEquals(TEST_ID_VAL, user.get(TEST_ID_NAME).toString());
      assertEquals(TEST_NAME_VAL, user.get(TEST_NAME_NAME).toString());
      assertEquals(TEST_CITY_VAL, address.get(TEST_CITY_NAME).toString());
      assertEquals(TEST_STATE_VAL, address.get(TEST_STATE_NAME).toString());
      
    } catch (Exception e) {
      LOG.error("Error:", e);
      fail("Unexpected " + e.getClass().getSimpleName());
    }
  }

  /**
   * Resets the GenericRecord instances to their original values
   */
  private static void resetRecords() {
    address = new GenericData.Record(ADDRESS_SCHEMA);
    address.put(TEST_CITY_NAME, TEST_CITY_VAL);
    address.put(TEST_STATE_NAME, TEST_STATE_VAL);
    user = new GenericData.Record(USER_SCHEMA);
    user.put(TEST_ID_NAME, TEST_ID_VAL);
    user.put(TEST_NAME_NAME, TEST_NAME_VAL);
    user.put(TEST_ADDRESS_NAME, address);
  }
  
}
