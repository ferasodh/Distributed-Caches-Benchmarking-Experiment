package com.performance.infinispan;

import java.util.Map;
import java.util.Random;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkDriverAdapter;
import org.yardstickframework.BenchmarkUtils;

import com.performance.GeneralArguments;
import com.performance.model.Employee;
import com.performance.model.dataAPI;

import static org.yardstickframework.BenchmarkUtils.println;

/**
 * A benchmark for infinispan
 * @author Haytham Salhi
 *
 */
public class InfinispanPutDriverBenchmark extends BenchmarkDriverAdapter {
	
	// These our special args 
	private final InfinispanBenchmarkArguments args = new InfinispanBenchmarkArguments();
	
	private static String serverIP = GeneralArguments.serverIP;
	
	private RemoteCacheManager rcm;
	private RemoteCache<Integer, Employee> remoteMap;
	
	@Override
	public void setUp(BenchmarkConfiguration cfg) throws Exception {
		super.setUp(cfg);
		
		println("Started benchmark with id: " + cfg.memberId());
		
		BenchmarkUtils.jcommander(cfg.commandLineArguments(), args, "<infinispan-driver>");
		
		println("I'm the client I want to setup!");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.addServers(serverIP + ":11222;" + serverIP + ":11322;" + serverIP + ":11422;" + serverIP + ":11522");
		
		rcm = new RemoteCacheManager(cb.build());
		remoteMap = rcm.getCache();
		
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
}
