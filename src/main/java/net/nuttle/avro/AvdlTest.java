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

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.log4j.Logger;

import net.nuttle.avro.bo.TestRecord;

/**
 * Demonstrate use of avdl-generated classes
 */
public class AvdlTest {

    private static final Logger LOG = Logger.getLogger(AvdlTest.class);
    public static void main(String[] args) {
        write(new File("test.avro"));
        read(new File("test.avro"));
    }
    
    public static void read(File f) {
        DatumReader<TestRecord> datumReader = new SpecificDatumReader<TestRecord>(TestRecord.class);
        try {
            DataFileReader<TestRecord> fileReader = new DataFileReader<TestRecord>(f, datumReader);
            TestRecord record = null;
            while(fileReader.hasNext()) {
                record = fileReader.next(record);
                System.out.println(record.getId());
            }
            fileReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public static void write(File f) {
        TestRecord r = new TestRecord();
        r.setId(55);
        DatumWriter<TestRecord> datumWriter = new SpecificDatumWriter<TestRecord>(TestRecord.class);
        DataFileWriter<TestRecord> writer = new DataFileWriter<TestRecord>(datumWriter);
        try {
            writer.create(r.getSchema(), f);
            writer.append(r);
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        
    }
}
