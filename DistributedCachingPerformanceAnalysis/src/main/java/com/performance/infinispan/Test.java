package com.performance.infinispan;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;

import com.performance.model.Employee;
import com.performance.model.Organization;

public class Test {

	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.addServer().host("10.0.0.1")
		.port(11222);
		
		///API entry point, by default it connects to localhost:11222
		RemoteCacheManager rcm = new RemoteCacheManager(cb.build());
		
		//obtain a handle to the remote default cache
		RemoteCache<Integer, Employee> cache = rcm.getCache();
		
//		cache.put(2, new Employee(1, "Haytsssssssssssham", 12, "323", new Organization(2, "org", "dsd", "12")));
//		cache.put(3, new Employee(1, "Haytssssassssssham", 12, "323", new Organization(2, "org", "dsd", "12")));
//
//		cache.put(4, new Employee(1, "Hayt4234ssssssham", 12, "323", new Organization(2, "org", "dsd", "12")));
//
//		cache.put(5, new Employee(1, "Haytsfsdfssssssham", 12, "323", new Organization(2, "org", "dsd", "12")));
		
		
		System.out.println(cache.get(5));

	}

}
