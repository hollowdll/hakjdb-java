package com.github.hakjdb.hakjdbjava.requests;

import java.util.List;

public interface ServerRequests {
  /**
   * Returns information about the server as a JSON string. The output is divided into sections.
   *
   * @return JSON string of server information
   */
  String getServerInfo();

  /**
   * Returns the server logs if the server log file is enabled.
   *
   * @return A list of logs
   */
  List<String> getLogs();

  /**
   * Reloads the server configurations that can be reloaded at runtime from the server configuration
   * sources.
   */
  void reloadConfig();
}
