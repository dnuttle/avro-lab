package net.nuttle.avro.requestor;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;

import net.nuttle.avro.bo.account.AvroAccountProtocol;
import net.nuttle.avro.bo.account.AvroAccountRequest;
import net.nuttle.avro.bo.account.AvroAccountResponse;

/**
 *
 */
public class AvroAccountRequestor {
  
  //private static NettyServer server= new NettyServer(new SpecificResponder(AvroAccountProtocol.class, new AvroAccountImpl()), new InetSocketAddress(7001));
  
  public static class AvroAccountImpl implements AvroAccountProtocol {
    public AvroAccountResponse getBalance(AvroAccountRequest req) {
      AvroAccountResponse resp = new AvroAccountResponse();
      resp.setBalance(125F);
      resp.setId(req.getId());
      resp.setName("John Doe");
      return resp;
    }
  }


}
