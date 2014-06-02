package net.nuttle.avro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.nuttle.avro.bo.UFO_Sighting_Record;

/**
 * Simple demonstration of writing to and reading from avsc files.
 * An avsc file is a simple, older style of Avro schema. It is a simple JSON file from which a Java class is generated.
 * This generated class can then be used to serialize data to binary avro files, and deserialize from them, as shown below.
 */
public class AvscTest {
  
  private static final String DATA_PATH = "build/test/data";
  private static final String UFO_AVRO_NAME = "ufo.avro";

    public static void main(String[] args) throws Exception {
        //read(new File("ufo.avro"));
      List<UFO_Sighting_Record> recs = new ArrayList<UFO_Sighting_Record>();
      UFO_Sighting_Record record;
      File f = new File(DATA_PATH, UFO_AVRO_NAME);
      if(f.exists()) {
        f.delete();
      }
      /*
       * The following uses the AvroIO general-purpose class to write to a file
       */
      record = new UFO_Sighting_Record();
      record.setCity("Chicago");
      record.setDuration(12F);
      record.setShape("cigar");
      record.setSightingDate("1/1/13");
      recs.add(record);
      AvroIO.write(recs, UFO_Sighting_Record.class, new File(DATA_PATH, UFO_AVRO_NAME));
      
      /*
       * The following uses the AvroIO general-purpose class to read from a file.
       */
        
      recs = AvroIO.read(new UFO_Sighting_Record(), UFO_Sighting_Record.class, 
        new File(DATA_PATH, UFO_AVRO_NAME));
      for(UFO_Sighting_Record rec : recs) {
          System.out.println(rec.getCity());
          System.out.println(rec.getShape());
      }
    }
    
}
