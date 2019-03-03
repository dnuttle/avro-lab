package net.nuttle.avro;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.nuttle.avro.requestor.AvroAccountRequestor.AvroAccountImpl;
import net.nuttle.avro.bo.account.AvroAccountProtocol;
import net.nuttle.avro.bo.account.AvroAccountRequest;
import net.nuttle.avro.bo.account.AvroAccountResponse;

/**
 *
 */
public class AvroAccountRequestorTest {
  private static NettyServer server;

  @Before
  public void setUp() {
    server = new NettyServer(new SpecificResponder(AvroAccountProtocol.class, new AvroAccountImpl()), new InetSocketAddress(7001));
  }
  @After
  public void tearDown() {
	  server.close();
  }
  
  @Test
  public void testNetty() throws IOException {
    NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(server.getPort()));
    AvroAccountProtocol proxy = (AvroAccountProtocol) SpecificRequestor.getClient(AvroAccountProtocol.class, client);
    AvroAccountRequest req = new AvroAccountRequest();
    req.setId(12);
    AvroAccountResponse resp = proxy.getBalance(req);
    System.out.println("Balance for " + resp.getName() + ": " + resp.getBalance());
    client.close();
    server.close();
  }

}
