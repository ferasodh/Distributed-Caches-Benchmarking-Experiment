package com.performance.hazelcast;

import static org.yardstickframework.BenchmarkUtils.println;

import java.util.Map;

import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkServer;
import org.yardstickframework.BenchmarkUtils;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.performance.GeneralArguments;
import com.performance.hazelcast.HazelcastBenchmarkArguments;
import com.performance.model.Employee;
import com.performance.model.dataAPI;

/**
 * // Run this server as much as you want 
 * @author Haytham Salhi
 *
 */
public class HazelcastServerTest  {
	
	private static int cacheSize = 3;

	

	public static void main(String[] args) {
		// We can make a config for the map
		// MapConfig mapConfig = new MapConfig("employees");
		//mapConfig.setAsyncBackupCount(0);
        //mapConfig.setBackupCount(0);
        //mapConfig.setStatisticsEnabled(false);
		
		// You can config either programatically or declaretively, see more in doc for how the config is initialized
		Config cfg = new Config();
		//cfg.addMapConfig(mapConfig);
		
		// To enable the rest APIs
		//cfg.setProperty("hazelcast.rest.enabled", "true");
		
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        
        //initializeMaps(instance);
	}

	private static void initializeMaps(HazelcastInstance instance) {
        Map<Integer, Employee> map = instance.getMap("employees");
        
        if(map.isEmpty()) {
            dataAPI dataApi = new dataAPI();
            
            for (int i = 0; i < cacheSize; i++) {
    			map.put(i, dataApi.getEmployee(i));
    		}	
            System.out.println(map.get(3));
            System.out.println(map.get(cacheSize - 1));

            System.out.println("Initialization done!");
        }
	}
}
