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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import net.nuttle.avro.bo.UFO_Sighting_Record;

/**
 * Simple demonstration of writing to and reading from avsc files.
 * An avsc file is a simple, older style of Avro schema. It is a simple JSON file from which a Java class is generated.
 * This generated class can then be used to serialize data to binary avro files, and deserialize from them, as shown below.
 */
public class AvscTest {

    public static void main(String[] args) {
        //read(new File("ufo.avro"));

        /*
         * The following uses the AvroIO general-purpose class to write to a file
         */
        /*
        UFO_Sighting_Record record = new UFO_Sighting_Record();
        record.setCity("Chicago");
        record.setDuration(12F);
        record.setShape("cigar");
        record.setSightingDate("1/1/13");
        List<UFO_Sighting_Record> recs = new ArrayList<UFO_Sighting_Record>();
        recs.add(record);
        AvroIO.write(recs, UFO_Sighting_Record.class, new File("tmp.avro"));
        */
        
        /*
         * The following uses the AvroIO general-purpose class to read from a file.
         */
        List<UFO_Sighting_Record> recs = AvroIO.read(new UFO_Sighting_Record(), UFO_Sighting_Record.class, new File("ufo.avro"));
        for(UFO_Sighting_Record record : recs) {
            System.out.println(record.getCity());
            System.out.println(record.getShape());
        }
    }
    
    /**
     * This is a specific example of reading from an Avro data file.
     */
    private static void read(File file) {
        DatumReader<UFO_Sighting_Record> datumReader = new SpecificDatumReader<UFO_Sighting_Record>(UFO_Sighting_Record.class);
        try {
            DataFileReader<UFO_Sighting_Record> fileReader = new DataFileReader<UFO_Sighting_Record>(file, datumReader);
            UFO_Sighting_Record record = null;
            while(fileReader.hasNext()) {
                record = fileReader.next(record);
                System.out.println(record.getCity());
            }
            fileReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * This is a specific example of writing to an Avro data file.
     * @param file
     */
    private static void write(File file)  {
        UFO_Sighting_Record record = new UFO_Sighting_Record();
        record.setCity("Chicago");
        record.setDuration(12F);
        record.setShape("cigar");
        record.setSightingDate("1/1/13");
        
        UFO_Sighting_Record record2 = UFO_Sighting_Record.newBuilder()
                .setCity("Detroit")
                .setDuration(13F)
                .setShape("oval")
                .setSightingDate("1/2/13")
                .build();
        DatumWriter<UFO_Sighting_Record> datumWriter = new SpecificDatumWriter<UFO_Sighting_Record>(UFO_Sighting_Record.class);
        DataFileWriter<UFO_Sighting_Record> writer = new DataFileWriter<UFO_Sighting_Record>(datumWriter);
        try {
            writer.create(record.getSchema(), file);
            writer.append(record);
            writer.append(record2);
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
