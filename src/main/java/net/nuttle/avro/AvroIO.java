package net.nuttle.avro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

/**
 * My attempt at a generic Avro IO class.
 * It appears that you can't get the class of a generic, so clazz param needed.
 * Found references to tricky, maybe questionabile ways of getting around the need for clazz, not sure I like them.
 */
public class AvroIO {

    public static <T extends SpecificRecord> void write(List<T> records, Class<T> clazz, File f) 
      throws IOException {
        if(records==null || records.size()==0) {
            return;
        }
        T record = records.get(0);
        DatumWriter<T> datumWriter = new SpecificDatumWriter<T>(clazz);
        DataFileWriter<T> writer = new DataFileWriter<T>(datumWriter);
        try {
            writer.create(record.getSchema(), f);
            for(T rec : records) {
                writer.append(rec);
            }
        } finally {
            closeQuietly(writer);
        }
    }
    
    public static <T extends SpecificRecord> List<T> read(T record, Class<T> clazz, File f) 
      throws IOException {
        DatumReader<T> datumReader = new SpecificDatumReader<T>(clazz);
        List<T> recs = new ArrayList<T>();
        DataFileReader<T> fileReader = null;
        try {
          fileReader = new DataFileReader<T>(f, datumReader);
          record = null;
          while(fileReader.hasNext()) {
              record = fileReader.next(record);
              recs.add(record);
              record = null;
          }
        } finally {
          closeQuietly(fileReader);
        }
        return recs;
    }
    
    public static <T> void closeQuietly(DataFileReader<T> reader) {
      if(reader==null) {
        return;
      }
      try {
        reader.close();
      } catch (Exception e) {
        //do nothing
      }
    }
    
    public static <T> void closeQuietly(DataFileWriter<T> writer) {
      if(writer==null) {
        return;
      }
      try {
        writer.close();
      } catch (Exception e) {
        //do nothing
      }
    }
}
