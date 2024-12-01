package com.github.hakjdb.hakjdbjava.requests;

import java.util.Set;

public interface GeneralKeyValueRequestSender {
    /**
     * Sends getKeys request.
     *
     * @return Retrieved keys
     */
    Set<String> sendRequestGetKeys();
}
