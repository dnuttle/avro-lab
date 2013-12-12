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

    public static <T extends SpecificRecord> void write(List<T> records, Class clazz, File f) {
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
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public static <T extends SpecificRecord> List<T> read(T record, Class clazz, File f) {
        DatumReader<T> datumReader = new SpecificDatumReader<T>(clazz);
        List<T> recs = new ArrayList<T>();
        try {
            DataFileReader<T> fileReader = new DataFileReader<T>(f, datumReader);
            record = null;
            while(fileReader.hasNext()) {
                record = fileReader.next(record);
                recs.add(record);
                record = null;
            }
            fileReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return recs;
    }
}
