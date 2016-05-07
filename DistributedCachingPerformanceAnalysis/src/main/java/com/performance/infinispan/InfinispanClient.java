package com.performance.infinispan;

import java.io.IOException;
import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.DescriptorParserException;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import com.performance.model.Employee;


public class InfinispanClient {
	
	public static void main(String[] args) throws DescriptorParserException, IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.addServer().host("localhost")
		.port(11222).marshaller(new ProtoStreamMarshaller());
		
		
		///API entry point, by default it connects to localhost:11222
		RemoteCacheManager rcm = new RemoteCacheManager(cb.build());
		
		
		SerializationContext srcCtx = ProtoStreamMarshaller.getSerializationContext(rcm);
		
		srcCtx.registerProtoFiles(FileDescriptorSource.fromResources("com/proto/library.proto"));
		srcCtx.registerMarshaller(new EmployeeMarshaller());
		
	    ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
	    String memoSchemaFile = protoSchemaBuilder
                .fileName("library.proto")
                .packageName("com.proto")
                .addClass(Employee.class)
                .build(srcCtx);
	    
	    RemoteCache<String, String> metadataCache = rcm.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
        metadataCache.put("library.proto", memoSchemaFile);
		
		//obtain a handle to the remote default cache
		RemoteCache<Integer, Object> cache = rcm.getCache();
		
		//cache.put(1, "Haytham");
		
//		Employee emp = new Employee("Ahmad", "25");
//		cache.put(2, emp);
//		
//		System.out.println(cache.get(2));
		
		QueryFactory<Query> qf = Search.getQueryFactory(cache);
		
		Query query = qf.from(Employee.class)
		.having("name").like("%s%")
		.toBuilder().build();
		
		List<Employee> result = query.list();
		
		System.out.println(result);
		
		
		rcm.stop();
	}
	
	private static class EmployeeMarshaller implements MessageMarshaller<Employee> {
		public EmployeeMarshaller() {
		}

		@Override
		public Class<? extends Employee> getJavaClass() {
			// TODO Auto-generated method stub
			return Employee.class;
		}

		@Override
		public String getTypeName() {
			// TODO Auto-generated method stub
			return "com.ignite.model.Employee";
		}

		@Override
		public Employee readFrom(
				org.infinispan.protostream.MessageMarshaller.ProtoStreamReader reader)
				throws IOException {
			String name = reader.readString("name");
			Integer age = reader.readInt("age");
			return null;
			//return new Employee(name, age);
		}

		@Override
		public void writeTo(
				org.infinispan.protostream.MessageMarshaller.ProtoStreamWriter writer,
				Employee t) throws IOException {
			writer.writeString("name", t.getName());
			writer.writeInt("age", t.getAge());
		}
	}


}
