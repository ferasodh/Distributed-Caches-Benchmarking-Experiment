package com.performance.hazelcast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.performance.model.Employee;

/**
 * A benchmark for hazelcast
 * @author Haytham Salhi
 *
 */
@State(value = Scope.Thread)
@OperationsPerInvocation(HazelcastBenchmark.OPERATIONS_PER_INVOCATION)
public class HazelcastBenchmark {
	
	public static final int OPERATIONS_PER_INVOCATION = 50000; 
	private HazelcastInstance hzClient;
	private IMap<Integer, Employee> remoteMap;
	
	// You could have more methods annotated with @Setup and @TearDown.
	@Setup(Level.Trial)
	public void doSetup() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress("192.168.1.86:5701", "192.168.1.86:5702");
		
		hzClient = HazelcastClient.newHazelcastClient(clientConfig);
		
		remoteMap = hzClient.getMap("employees");
		
		
		
		// Line
		
		
		
	}
	
	@TearDown(Level.Trial)
	public void doTearDown() {
		HazelcastClient.shutdownAll();
	}
	
	
    @Benchmark
    @BenchmarkMode(Mode.Throughput) // It tells JMH what you want to measure!
    @OutputTimeUnit(TimeUnit.SECONDS) // Specifies what time units you want the benchmark results printed in
    public void getPerformanceTest(Blackhole blackhole) {
    	Random random = new Random();
    	
    	for (int i = 0; i < OPERATIONS_PER_INVOCATION; i++) {
			int x = random.nextInt(remoteMap.size());
			Employee emp = remoteMap.get(x);
			
			blackhole.consume(emp);
		}	
    }
    
    
    
}
