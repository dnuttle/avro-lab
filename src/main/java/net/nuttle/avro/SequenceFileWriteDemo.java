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

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IOUtils;

/**
 *
 */
public class SequenceFileWriteDemo {
  private static final String[] DATA = { "One, two, buckle my shoe", 
    "Three, four, shut the door", 
    "Five, six, pick up sticks", 
    "Seven, eight, lay them straight", 
    "Nine, ten, a big fat hen" };

  public static void main( String[] args) throws IOException { 
    //String uri = args[0]; 
    String uri = "/users/dnuttle/test.seq";
    Configuration conf = new Configuration(); 
    FileSystem fs = FileSystem.get(URI.create( uri), conf);
    Path path = new Path( uri); 
    IntWritable key = new IntWritable(); 
    Text value = new Text(); 
    SequenceFile.Writer writer = null; 
    try { 
      writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass()); 
      for (int i = 0; i < 100; i++) { 
        key.set( 100 - i); 
        value.set(DATA[i % DATA.length]); 
        System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value); 
        writer.append( key, value); 
      } 
    } finally { 
      IOUtils.closeStream( writer); 
    } 
  }
}
