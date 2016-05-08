package com.performance.infinispan;

import com.beust.jcommander.Parameter;

public class InfinispanBenchmarkArguments {
	
	/** Port echo server listens on. */
    @SuppressWarnings("FieldCanBeLocal")
    @Parameter(names = "--port", description = "Echo server port")
    private int port = 54321;

    /** Local address the server listens on. */
    @SuppressWarnings("FieldCanBeLocal")
    @Parameter(names = "--localBind", description = "Echo server local bind address")
    private String localbind = "127.0.0.1";

    /**
     * @return Client flag.
     */
    public int port() {
        return port;
    }

    /**
     * @return Local host.
     */
    public String localBind() {
        return localbind;
    }

    /**
     * @return Description.
     */
    public String description() {
        return "--localBind=" + localbind + "--port=" + port;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return this.getClass().getSimpleName() + " [" +
            "port=" + port +
            ", localBind='" + localbind + '\'' +
            ']';
    }
}
