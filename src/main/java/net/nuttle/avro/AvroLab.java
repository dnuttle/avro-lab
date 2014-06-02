package net.nuttle.avro;

import java.io.IOException;
import net.nuttle.avro.requestor.AvroAccountRequestor;

/**
 *
 */
public class AvroLab {
  
  //private static final int DFS_REPLICATION_INTERVAL = 1;
  //private static final int DATANODE_COUNT = 3;
  
  public static void main(String[] args) throws IOException {
    AvroAccountRequestor.testNetty();
  }
  /*
  public static void startCluster()  {
    try {
      Configuration conf = new Configuration();
      conf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, 100);
      conf.setInt(DFSConfigKeys.DFS_BYTES_PER_CHECKSUM_KEY, 1);
      conf.setLong(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY, DFS_REPLICATION_INTERVAL);
      conf.setInt(DFSConfigKeys.DFS_NAMENODE_REPLICATION_INTERVAL_KEY,DFS_REPLICATION_INTERVAL);

      MiniDFSCluster dfsCluster = new MiniDFSCluster.Builder(conf).numDataNodes(3).build();//new MiniDFSCluster(conf, DATANODE_COUNT, true, null);
      FileSystem fs = dfsCluster.getFileSystem();
      dfsCluster.shutdown();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  */
}
