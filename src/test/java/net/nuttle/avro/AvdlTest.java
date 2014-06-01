/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2012 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package net.nuttle.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.log4j.Logger;
import org.junit.Test;

import net.nuttle.avro.bo.TestRecord;
import static org.junit.Assert.assertEquals;

/**
 * Demonstrate use of avdl-generated classes.
 * Uses only streams, no file IO.
 */
public class AvdlTest {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(AvdlTest.class);
    public static final int TEST_ID = 55;
    
    @Test
    public void testReadWrite() throws UnsupportedEncodingException, IOException {
      //Create a specific record, TestRecord, and write it to output stream twice
      TestRecord r = new TestRecord();
      r.setId(TEST_ID);
      DatumWriter<TestRecord> datumWriter = new SpecificDatumWriter<TestRecord>(TestRecord.class);
      DataFileWriter<TestRecord> writer = new DataFileWriter<TestRecord>(datumWriter);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
          writer.create(r.getSchema(), baos);
          writer.append(r);
          writer.append(r);
          writer.close();
      } catch (IOException ioe) {
          ioe.printStackTrace();
      }
      //Convert output into input stream, read it, confirm that 
      //id of each record is correct, and that there are two records
      DatumReader<TestRecord> datumReader = new SpecificDatumReader<TestRecord>(TestRecord.class);
      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      baos.close();
      DataFileStream<TestRecord> streamReader = new DataFileStream<TestRecord>(bais, datumReader);
      TestRecord record = null;
      int count = 0;
      while(streamReader.hasNext()) {
          record = streamReader.next(record);
          count++;
          assertEquals(TEST_ID, record.getId().intValue());
      }
      streamReader.close();
      bais.close();
      assertEquals(2, count);
    }
    
}
