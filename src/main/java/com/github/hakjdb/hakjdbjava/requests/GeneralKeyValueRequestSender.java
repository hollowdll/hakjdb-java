package com.github.hakjdb.hakjdbjava.requests;

import java.util.Set;

public interface GeneralKeyValueRequestSender {
    /**
     * Send getKeys request.
     *
     * @return Retrieved keys
     */
    Set<String> sendRequestGetKeys();
}
