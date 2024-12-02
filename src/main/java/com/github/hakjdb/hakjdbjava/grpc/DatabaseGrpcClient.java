package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.dbpb.DBServiceGrpc;
import com.github.hakjdb.hakjdbjava.api.v1.dbpb.Db;
import io.grpc.Channel;

import java.util.concurrent.TimeUnit;

/** Grpc client for the service DBservice */
public class DatabaseGrpcClient {
  private final DBServiceGrpc.DBServiceBlockingStub stub;

  public DatabaseGrpcClient(Channel channel) {
    this.stub = DBServiceGrpc.newBlockingStub(channel);
  }

  /**
   * Calls the RPC CreateDB
   *
   * @param request RPC request
   * @param timeoutSeconds RPC timeout in seconds
   * @return RPC response
   */
  public Db.CreateDBResponse createDatabase(Db.CreateDBRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).createDB(request);
  }

  /**
   * Calls the RPC GetAllDBs
   *
   * @param request RPC request
   * @param timeoutSeconds RPC timeout in seconds
   * @return RPC response
   */
  public Db.GetAllDBsResponse getDatabase(Db.GetAllDBsRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).getAllDBs(request);
  }

  /**
   * Calls the RPC GetDBInfo
   *
   * @param request RPC request
   * @param timeoutSeconds RPC timeout in seconds
   * @return RPC response
   */
  public Db.GetDBInfoResponse getDatabaseInfo(Db.GetDBInfoRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).getDBInfo(request);
  }

  /**
   * Calls the RPC DeleteDB
   *
   * @param request RPC request
   * @param timeoutSeconds RPC timeout in seconds
   * @return RPC response
   */
  public Db.DeleteDBResponse deleteDatabase(Db.DeleteDBRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).deleteDB(request);
  }

  /**
   * Calls the RPC ChangeDB
   *
   * @param request RPC request
   * @param timeoutSeconds RPC timeout in seconds
   * @return RPC response
   */
  public Db.ChangeDBResponse changeDatabase(Db.ChangeDBRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).changeDB(request);
  }
}
