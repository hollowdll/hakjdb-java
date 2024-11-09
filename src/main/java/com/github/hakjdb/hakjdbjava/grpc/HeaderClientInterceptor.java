package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.*;

import java.util.concurrent.atomic.AtomicReference;

public class HeaderClientInterceptor implements ClientInterceptor {
    private final AtomicReference<Metadata> metadataRef;

    public HeaderClientInterceptor(AtomicReference<Metadata> metadataRef) {
        this.metadataRef = metadataRef;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.merge(metadataRef.get());
                super.start(responseListener, headers);
            }
        };
    }
}
