package com.performance.infinispan;

import java.io.IOException;
import java.util.Map;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.DescriptorParserException;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import com.performance.model.Employee;
import com.performance.model.Organization;

public class Test2 {

	public static void main(String[] args) throws DescriptorParserException, IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.addServers("10.0.0.1:11222;10.0.0.1:11322");
		
		///API entry point, by default it connects to localhost:11222
		RemoteCacheManager rcm = new RemoteCacheManager(cb.build());
		
		//obtain a handle to the remote default cache
		RemoteCache<Integer, Employee> cache = rcm.getCache();
		
		System.out.println(cache.get(1));

	}
}
