package com.performance.hazelcast;

import static org.yardstickframework.BenchmarkUtils.println;

import java.util.Map;

import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkServer;
import org.yardstickframework.BenchmarkUtils;
import org.yardstickframework.examples.echo.EchoBenchmarkArguments;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.performance.model.Employee;

/**
 * // Run this server as much as you want 
 * @author Haytham Salhi
 *
 */
public class HazelcastServer implements BenchmarkServer {
	
	// These our special args 
	private final EchoBenchmarkArguments args = new EchoBenchmarkArguments();

	@Override
	public void start(BenchmarkConfiguration cfg) throws Exception {
        BenchmarkUtils.jcommander(cfg.commandLineArguments(), args, "<hazelcast-server>");
        
        println("Started server with id: " + cfg.memberId());
        
		// We can make a config for the map
		// MapConfig mapConfig = new MapConfig("employees");
		//mapConfig.setAsyncBackupCount(0);
        //mapConfig.setBackupCount(0);
        //mapConfig.setStatisticsEnabled(false);
		
		// You can config either programatically or declaretively, see more in doc for how the config is initialized
		Config hzlCfg = new Config();
		//cfg.addMapConfig(mapConfig);
		
		// To enable the rest APIs
		//cfg.setProperty("hazelcast.rest.enabled", "true");
		
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(hzlCfg);
        
        initializeMaps(instance);
        
        println("I'm going to run forever");
	}

	@Override
	public void stop() throws Exception {
		println("I'm stopped now!!!");
	}

	@Override
	public String usage() {
		return BenchmarkUtils.usage(args);
	}
	
	

//	public static void main(String[] args) {
//		// We can make a config for the map
//		// MapConfig mapConfig = new MapConfig("employees");
//		//mapConfig.setAsyncBackupCount(0);
//        //mapConfig.setBackupCount(0);
//        //mapConfig.setStatisticsEnabled(false);
//		
//		// You can config either programatically or declaretively, see more in doc for how the config is initialized
//		Config cfg = new Config();
//		//cfg.addMapConfig(mapConfig);
//		
//		// To enable the rest APIs
//		//cfg.setProperty("hazelcast.rest.enabled", "true");
//		
//        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
//        
//        //initializeMaps(instance);
//	}
//
	private static void initializeMaps(HazelcastInstance instance) {
        Map<Integer, Employee> map = instance.getMap("employees");
        
        if(map.isEmpty()) {
            int size = 5000;
            
            for (int i = 0; i < size; i++) {
    			map.put(i, new Employee("Employee name " + i, getRandomAge()));
    		}	
            
            System.out.println("Initialization done!");
        }
	}

	private static String getRandomAge() {
		double rand = Math.random();
		rand = rand * 100;
		
		int intRand = (int)rand;
		
		if(intRand >= 75) {
			return "30";
		} else if (intRand < 75 && intRand > 40) {
			return "25";
		} else {
			return "20";
		}
	}
}
