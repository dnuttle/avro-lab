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
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
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
