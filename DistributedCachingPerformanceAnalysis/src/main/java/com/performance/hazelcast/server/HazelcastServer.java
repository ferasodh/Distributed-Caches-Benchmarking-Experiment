package com.performance.hazelcast.server;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.performance.model.Employee;

/**
 * // Run this server as much as you want 
 * @author Haytham Salhi
 *
 */
public class HazelcastServer {

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
        
        int size = 5000;
        
        for (int i = 0; i < size; i++) {
			map.put(i, new Employee("Employee name " + i, getRandomAge()));
		}	
        
        System.out.println("Initialization done!");
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
