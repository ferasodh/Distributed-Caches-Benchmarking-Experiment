package com.performance.infinispan.server;

import java.io.IOException;
import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.commons.api.BasicCache;
import org.infinispan.protostream.DescriptorParserException;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;


public class SimpleInfinispanClient {
	
	public static void main(String[] args) throws DescriptorParserException, IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.addServer().host("localhost")
		.port(11324);
		
		///API entry point, by default it connects to localhost:11222
		RemoteCacheManager rcm = new RemoteCacheManager(cb.build());
	    
		//obtain a handle to the remote default cache
		RemoteCache<Object, Object> cache = rcm.getCache();
		//BasicCache<Object, Object> cache = rcm.getCache();
		
		//System.out.println(cache.put(1, "Haytham"));
		System.out.println(cache.get(1));
	
		
		rcm.stop();
	}
	

}
