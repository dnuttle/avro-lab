package net.nuttle.avro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.nuttle.avro.json.JsonSchemas;
//import net.nuttle.hbase.HBaseLab;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
/*
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.junit.Test;
*/
/**
 * All commented out for now; this really shouldn't drag an hbase dependency into an avro lab.
 * Maybe need another project that is a combined hbase-avro lab.
 * @author dnuttle
 *
 */
public class AvroLabHBaseTest {
/*
  private static final Logger LOG = Logger.getLogger(AvroLabHBaseTest.class);
  private static final Configuration CONF = HBaseConfiguration.create();
  private static HTablePool pool = null;
  
  private static final String AVRO_TABLE = "avro_table";
  private static final String AVRO_FAMILY = "f";
  private static final String AVRO_QUALIFIER = "col1";

  @Test
  public void test2() throws IOException {
    boolean s = HBaseLab.isTableAvailable(CONF, AVRO_TABLE);
    LOG.debug("Found table: " + s);
  }
*/  
  /**
   * Writes a generic record, based on a JSON schema, to HBase,
   * reads it back, confirms that values are correct.
   * Currently, this hoses the zookeeper every time it runs,
   * creating a zombie avro_table.
   * @throws Exception
   */
/*
  @Test
  public void test() throws Exception {
    String key = "ABCD";
    CONF.set("hbase.client.pause", "30");
    CONF.set("hbase.client.retries.number", "2");
    CONF.set("hbase.tmp.dir", "./tmp");
    LOG.info("hbase.rootdir: " + CONF.get("hbase.rootdir"));
    LOG.debug("0");
    if(!HBaseLab.isTableAvailable(CONF, AVRO_TABLE)) {
      LOG.info("Creating table");
      HBaseLab.createTable(CONF, AVRO_TABLE, AVRO_FAMILY);
    } else {
      LOG.debug("Avro table exists");
    }
    assertTrue(HBaseLab.isTableAvailable(CONF, AVRO_TABLE));
    HTableInterface table = getPool(CONF).getTable(AVRO_TABLE);
    Parser parser = new Parser();
    Schema schema = parser.parse(JsonSchemas.ADDRESS_SCHEMA);
    GenericRecord record = new GenericData.Record(schema);
    
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    record.put("city", "algonquin");
    record.put("state", "il");
    Encoder encoder = EncoderFactory.get().binaryEncoder(bos, null);

    GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
    datumWriter.write(record, encoder);
    encoder.flush();
    Put p = new Put(Bytes.toBytes(key));
    byte[] data = bos.toByteArray();
    p.add(Bytes.toBytes(AVRO_FAMILY), Bytes.toBytes(AVRO_QUALIFIER), data);
    LOG.info(data.length);
    table.put(p);
    table.flushCommits();
    table.close();
    //Now read back
    DatumReader<GenericRecord> dr = new GenericDatumReader<GenericRecord>(schema);
    table = pool.getTable(AVRO_TABLE);
    Get g = new Get(Bytes.toBytes(key));
    g.setMaxVersions(1);
    Result r = table.get(g);
    byte[] result = r.getValue(Bytes.toBytes(AVRO_FAMILY), Bytes.toBytes(AVRO_QUALIFIER));
    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(result, null);
    record = dr.read(null, decoder);
    assertEquals(record.get("city").toString(), "algonquin");
    assertEquals(record.get("state").toString(), "il");
    //HBaseLab.dropTable(CONF, AVRO_TABLE);
  }
  
  private static HTablePool getPool(Configuration conf) {
    if(pool == null) {
      pool = new HTablePool(conf, 100);
    }
    return pool;
  }
  */
}
