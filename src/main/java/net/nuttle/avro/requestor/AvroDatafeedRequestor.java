package net.nuttle.avro.requestor;

import net.nuttle.avro.bo.datafeed.AvroDatafeedProtocol;
import net.nuttle.avro.bo.datafeed.AvroDatafeedRequest;
import net.nuttle.avro.bo.datafeed.AvroDatafeedResponse;

public class AvroDatafeedRequestor {
  public static class AvroDatafeedImpl implements AvroDatafeedProtocol {
    public AvroDatafeedResponse getResult(AvroDatafeedRequest req) {
      AvroDatafeedResponse resp = new AvroDatafeedResponse();
      System.out.println("LINES RECEIVED: " + req.getLines().size());
      resp.setResponseCode(0);
      return resp;
    }
  }

}
