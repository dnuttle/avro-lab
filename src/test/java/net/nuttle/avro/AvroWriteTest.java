package net.nuttle.avro;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.nuttle.avro.bo.datafeed.AvroDatafeedLine;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class AvroWriteTest {

  /*
   * Purpose of test is to write many records into large file and demonstrate
   * that it does not consume large amount of memory
   */
  @Test
  public void testWrite() throws FileNotFoundException, IOException, InterruptedException {
    /*
    FileOutputStream fos = new FileOutputStream(new File("/Users/dnuttle/test.avro"));
    Encoder e = EncoderFactory.get().binaryEncoder(fos, null);
    SpecificDatumWriter<AvroDatafeedLine> writer = new SpecificDatumWriter<AvroDatafeedLine>(AvroDatafeedLine.class);
    AvroDatafeedLine line = new AvroDatafeedLine();
    line = new AvroDatafeedLine();
    List<CharSequence> fields = new ArrayList<CharSequence>();
    fields.add("a");
    fields.add("b");
    line.setFields(fields);
    for(int i=0;i<1000;i++) {
      writer.write(line, e);
    }
    e.flush();
    fos.close();
    */
    
    SpecificDatumWriter<AvroDatafeedLine> writer = new SpecificDatumWriter<AvroDatafeedLine>(AvroDatafeedLine.class);
    DataFileWriter<AvroDatafeedLine> dfw = new DataFileWriter<AvroDatafeedLine>(writer);
    dfw.create(AvroDatafeedLine.SCHEMA$, new File("/Users/dnuttle/test.avro"));
    AvroDatafeedLine line = new AvroDatafeedLine();
    List<CharSequence> fields = new ArrayList<CharSequence>();
    fields.add("a");
    fields.add("b");
    line.setFields(fields);
    for(int j=0;j<10;j++) {
      for(int i=0; i<10; i++) {
        dfw.append(line);
      }
      dfw.flush();
      //Commented out for now, throwing IOException Invalid sync, some kind of avro problem, possibly bug
      //Thread.sleep(1000);
    }
    dfw.close();
    
    SpecificDatumReader<AvroDatafeedLine> reader = new SpecificDatumReader<AvroDatafeedLine>(AvroDatafeedLine.class);
    DataFileReader<AvroDatafeedLine> dfr = new DataFileReader<AvroDatafeedLine>(new File("/Users/dnuttle/test.avro"), reader);
    int count=0;
    while(dfr.hasNext()) {
      count++;
      line = dfr.next();
      //System.out.println(line.getFields().size());
    }
    dfr.close();
    //System.out.println("Records: " + count);
    /*
    FileInputStream fis = new FileInputStream(new File("/Users/dnuttle/test.avro"));
    Decoder d = DecoderFactory.get().binaryDecoder(fis, null);
    SpecificDatumReader<AvroDatafeedLine> reader = new SpecificDatumReader<AvroDatafeedLine>(AvroDatafeedLine.class);
    
    while((line = reader.read(null, d))!=null) {
      fields = line.getFields();
      System.out.println(fields.size());
    }
    fis.close();
    */
  }
}
