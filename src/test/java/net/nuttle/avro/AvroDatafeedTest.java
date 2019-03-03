package net.nuttle.avro;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.nuttle.avro.bo.datafeed.AvroDatafeedLine;
import net.nuttle.avro.bo.datafeed.AvroDatafeedProtocol;
import net.nuttle.avro.bo.datafeed.AvroDatafeedRequest;
import net.nuttle.avro.bo.datafeed.AvroDatafeedResponse;
import net.nuttle.avro.bo.datafeed.AvroDatafeedSchema;
import net.nuttle.avro.bo.datafeed.AvroDatafeedSchemaField;
import net.nuttle.avro.bo.datafeed.DatafeedSchemaHelper;
import net.nuttle.avro.requestor.AvroDatafeedRequestor.AvroDatafeedImpl;

import org.apache.avro.Protocol;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AvroDatafeedTest {
  private static NettyServer server;

  @Before
  public void setUp() {
    server = new NettyServer(new SpecificResponder(AvroDatafeedProtocol.class, new AvroDatafeedImpl()), new InetSocketAddress(7001));
  }
  
  @After
  public void tearDown() {
    server.close();
  }

  @Test
  public void testFeed() throws IOException {
    NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(server.getPort()));
    List<AvroDatafeedLine> feed = new ArrayList<AvroDatafeedLine>();
    AvroDatafeedLine line;
    for(int i=0;i<1;i++) {  
      line = new AvroDatafeedLine(new ArrayList<CharSequence>());
      line.getFields().add("1000");
      line.getFields().add("Product title");
      line.getFields().add("Product description");
      line.getFields().add("99.99");
      line.getFields().add("Acme");
      line.getFields().add("Men");
      line.getFields().add("NE");
      feed.add(line);
	}
	AvroDatafeedSchema schema = new AvroDatafeedSchema();
	List<AvroDatafeedSchemaField> fields = new ArrayList<AvroDatafeedSchemaField>();
	fields.add(DatafeedSchemaHelper.buildSchemaField("prod_id", "java.lang.String"));
	fields.add(DatafeedSchemaHelper.buildSchemaField("title", "java.lang.String"));
	fields.add(DatafeedSchemaHelper.buildSchemaField("desc", "java.lang.String"));
	fields.add(DatafeedSchemaHelper.buildSchemaField("price", "java.math.BigDecimal"));
    fields.add(DatafeedSchemaHelper.buildSchemaField("brand", "java.lang.String"));
    fields.add(DatafeedSchemaHelper.buildSchemaField("category", "java.lang.String"));
    fields.add(DatafeedSchemaHelper.buildSchemaField("region", "java.lang.String"));
	schema.setFields(fields);
	int clientId = 1;
	AvroDatafeedProtocol proxy = (AvroDatafeedProtocol) SpecificRequestor.getClient(AvroDatafeedProtocol.class, client);
	
	//This method allows you to write your own getResult method, but it doesn't go through the Netty server
	/*
	AvroDatafeedProtocol proxy = new AvroDatafeedProtocol() {
	  @Override
	  public AvroDatafeedResponse getResult(AvroDatafeedRequest req) {
	    AvroDatafeedLine line = req.getLines().get(0);
	    Locale en_us = new Locale("en", "US");
	    DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(en_us);
	    nf.setParseBigDecimal(true);
	    BigDecimal price = (BigDecimal)nf.parse((String)line.getFields().get(3), new ParsePosition(0));
	    System.out.println(price);
	    AvroDatafeedResponse resp = new AvroDatafeedResponse();
	    resp.setResponseCode(1);
	    String s = new String("s");
	    try {
	      Class clazz = Class.forName("java.lang.String");
	      Object o = clazz.getConstructor(String.class).newInstance(new Object[]{"a value"});
	      System.out.println(o.toString());
	    } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
	      e.printStackTrace();
	    }
	    return resp;
	  }
	};
  */  
    //Protocol remote = SpecificRequestor.getRemote(proxy);
    //System.out.println("Protocol: " + remote.getClass().getName());

	AvroDatafeedRequest req = new AvroDatafeedRequest();
	req.setClientid(clientId);
	req.setDatafeedschema(schema);
	req.setLines(feed);
	AvroDatafeedResponse resp = proxy.getResult(req);
	System.out.println("Response: " + resp.getResponseCode());
	client.close();
	server.close();
  }

}
