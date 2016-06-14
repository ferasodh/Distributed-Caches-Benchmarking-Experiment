package com.performance.infinispan.server;

import java.io.IOException;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.DescriptorParserException;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import com.performance.GeneralArguments;
import com.performance.model.Employee;
import com.performance.model.Organization;
import com.performance.model.dataAPI;

public class ServerSimpleDataInitializer {
	
	// TODO
	private static final int cache_size = 0;
	private static String serverIP = GeneralArguments.serverIP;
	
	public static void main(String[] args) throws DescriptorParserException, IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.addServers(serverIP + ":11222;" + serverIP + ":11322;" + serverIP + ":11422;" + serverIP + ":11522");
		
		///API entry point, by default it connects to localhost:11222
		RemoteCacheManager rcm = new RemoteCacheManager(cb.build());
		
		//obtain a handle to the remote default cache
		RemoteCache<Integer, Employee> cache = rcm.getCache();
		
        if(cache.isEmpty()) {
            dataAPI dataApi = new dataAPI();
            
            for (int i = 0; i < cache_size; i++) {
            	cache.put(i, dataApi.getEmployee(i));
    		}	
            
            System.out.println(cache.get(3));
            System.out.println("Initialization done!");
        }
		
		rcm.stop();
	}
}
