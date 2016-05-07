package com.performance.hazelcast;

import java.util.Map;
import java.util.Random;

import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkDriverAdapter;
import org.yardstickframework.BenchmarkUtils;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.performance.GeneralArguments;
import com.performance.model.Employee;
import com.performance.model.dataAPI;

import static org.yardstickframework.BenchmarkUtils.println;

/**
 * A benchmark for hazelcast
 * @author Haytham Salhi
 *
 */
public class HazelcastPutDriverBenchmark extends BenchmarkDriverAdapter {
	
	// These our special args 
	private final HazelcastBenchmarkArguments args = new HazelcastBenchmarkArguments();
	
	private static String serverIP = GeneralArguments.serverIP;
	
	private HazelcastInstance hzClient;
	private IMap<Integer, Employee> remoteMap;
	
	@Override
	public void setUp(BenchmarkConfiguration cfg) throws Exception {
		super.setUp(cfg);
		
		println("Started benchmark with id: " + cfg.memberId());
		
		BenchmarkUtils.jcommander(cfg.commandLineArguments(), args, "<hazelcast-driver>");
		
		println("I'm the client I want to setup!");
		
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(serverIP + ":5701", serverIP + ":5702", serverIP + ":5703", serverIP + ":5704");
		
		hzClient = HazelcastClient.newHazelcastClient(clientConfig);
		remoteMap = hzClient.getMap("employees");
		
		println("I finished the setup!");
	}
	
	@Override
	public void tearDown() throws Exception {
		
		super.tearDown();
		
		println("I'm tearing down!!!");
		
		HazelcastClient.shutdownAll();
	}

	@Override
	public boolean test(Map<Object, Object> ctx) throws Exception {
		println("I'm the test");
		println("Args:" + args.toString());
		
		Random random = new Random();
		int i = random.nextInt(remoteMap.size());
		
		dataAPI dataApi = new dataAPI();
		
		Employee emp = dataApi.getEmployee(i);
		
		remoteMap.put(i, emp);
		
		println("I finished with emp = " + emp);
		
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
	
//	public static final int OPERATIONS_PER_INVOCATION = 50000; 
//	private HazelcastInstance hzClient;
//	private IMap<Integer, Employee> remoteMap;
//	
//	// You could have more methods annotated with @Setup and @TearDown.
//	public void doSetup() {
//		ClientConfig clientConfig = new ClientConfig();
//		clientConfig.getNetworkConfig().addAddress("192.168.1.86:5701", "192.168.1.86:5702");
//		
//		hzClient = HazelcastClient.newHazelcastClient(clientConfig);
//		
//		remoteMap = hzClient.getMap("employees");
//		
//		// Line
//		
//	}
//	
//	public void doTearDown() {
//		HazelcastClient.shutdownAll();
//	}
//	
//	
//    public void getPerformanceTest() {
//    	Random random = new Random();
//    	
//    	for (int i = 0; i < OPERATIONS_PER_INVOCATION; i++) {
//			int x = random.nextInt(remoteMap.size());
//			Employee emp = remoteMap.get(x);
//			
//			//blackhole.consume(emp);
//		}	
//    }
}
