package com.performance.infinispan;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkDriverAdapter;
import org.yardstickframework.BenchmarkUtils;

import com.performance.GeneralArguments;
import com.performance.model.Employee;
import com.performance.model.Organization;

import static org.yardstickframework.BenchmarkUtils.println;

/**
 * A benchmark for infinispan
 * @author Haytham Salhi
 *
 */
public class InfinispanCustomComplexQuery2DriverBenchmark extends BenchmarkDriverAdapter {
	
	// These our special args 
	private final InfinispanBenchmarkArguments args = new InfinispanBenchmarkArguments();
	
	private static String serverIP = GeneralArguments.serverIP;
	
	private RemoteCacheManager rcm;
	private RemoteCache<Integer, Employee> remoteMap;
	private QueryFactory<Query> qf;
	
	@Override
	public void setUp(BenchmarkConfiguration cfg) throws Exception {
		super.setUp(cfg);
		
		println("Started benchmark with id: " + cfg.memberId());
		
		BenchmarkUtils.jcommander(cfg.commandLineArguments(), args, "<infinispan-driver>");
		
		println("I'm the client I want to setup!");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		//cb.addServer().host("10.0.0.1")
		//.port(11222).marshaller(new ProtoStreamMarshaller());
		
		cb.addServers(serverIP + ":11222;" 
		+ serverIP + ":11322;" 
		+ serverIP + ":11422;" 
		+ serverIP + ":11522")
		.marshaller(new ProtoStreamMarshaller());
		
		///API entry point, by default it connects to localhost:11222
		rcm = new RemoteCacheManager(cb.build());
		
		SerializationContext srcCtx = ProtoStreamMarshaller.getSerializationContext(rcm);
		
		// THE FOLLOING SHOULD BE /com/performance/infinispan/proto/library.proto"
		// but, when packaging using maven, this file des not exist, so I copy it in src/main/resources as well
		// So this config works when packaging using maven
		srcCtx.registerProtoFiles(FileDescriptorSource.fromResources("library.proto"));
		srcCtx.registerMarshaller(new EmployeeMarshaller());
		srcCtx.registerMarshaller(new OrganizationMarshaller());
		
	    ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
	    String memoSchemaFile = protoSchemaBuilder
                .fileName("library.proto")
                .packageName("com.performance.infinispan.proto")
                .addClass(Employee.class)
                .addClass(Organization.class)
                .build(srcCtx);
	    
	    RemoteCache<String, String> metadataCache = rcm.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
        metadataCache.put("library.proto", memoSchemaFile);
		
		//obtain a handle to the remote default cache
        remoteMap = rcm.getCache();
        
        qf = Search.getQueryFactory(remoteMap);
		
		println("I finished the setup!");
	}
	
	@Override
	public void tearDown() throws Exception {
		
		super.tearDown();
		
		println("I'm tearing down!!!");
		
		rcm.stop();
	}

	@Override
	public boolean test(Map<Object, Object> ctx) throws Exception {
		println("I'm the test");
		println("Args:" + args.toString());
		
    	//Random random = new Random();
		//int randomAge = random.nextInt(GeneralArguments.agesBound);
		
		Query query = qf.from(Employee.class)
				.having("age").lt(25)
				.or().having("age").gt(75)
				.toBuilder().build();
		
		List<Employee> employees = query.list();
		
		println("I finished with employees of size = " + employees.size());
		return true;
	}
	
	@Override
	public String description() {
		String desc = BenchmarkUtils.description(cfg, this);

        return desc.isEmpty() ?
            getClass().getSimpleName() + args.description() + cfg.defaultDescription() : desc;
	}
	
	@Override
	public String usage() {
		
		return BenchmarkUtils.usage(args);
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
			return "com.performance.model.Employee";
		}

		@Override
		public Employee readFrom(
				org.infinispan.protostream.MessageMarshaller.ProtoStreamReader reader)
				throws IOException {
			int id = reader.readInt("ID");
			String name = reader.readString("name");
			int age = reader.readInt("age");
			String password = reader.readString("password");
			Organization organization = reader.readObject("organization", Organization.class);
			
			return new Employee(id, name, age, password, organization);
		}

		@Override
		public void writeTo(
				org.infinispan.protostream.MessageMarshaller.ProtoStreamWriter writer,
				Employee t) throws IOException {
			writer.writeInt("ID", t.getID());
			writer.writeString("name", t.getName());
			writer.writeInt("age", t.getAge());
			writer.writeString("password", t.getPassword());
			writer.writeObject("organization", t.getOrganization(), Organization.class);
		}
	}
	
	private static class OrganizationMarshaller implements MessageMarshaller<Organization> {
		public OrganizationMarshaller() {
		}

		@Override
		public Class<? extends Organization> getJavaClass() {
			// TODO Auto-generated method stub
			return Organization.class;
		}

		@Override
		public String getTypeName() {
			// TODO Auto-generated method stub
			return "com.performance.model.Organization";
		}

		@Override
		public Organization readFrom(
				org.infinispan.protostream.MessageMarshaller.ProtoStreamReader reader)
				throws IOException {
			int id = reader.readInt("ID");
			String name = reader.readString("name");
			String acronym = reader.readString("acronym");
			String employeeNumber = reader.readString("employeeNumber");
			
			return new Organization(id, name, acronym, employeeNumber);
		}

		@Override
		public void writeTo(
				org.infinispan.protostream.MessageMarshaller.ProtoStreamWriter writer,
				Organization t) throws IOException {
			writer.writeInt("ID", t.getID());
			writer.writeString("name", t.getName());
			writer.writeString("acronym", t.getAcronym());
			writer.writeString("employeeNumber", t.getEmployeeNumber());
		}
	}
}
