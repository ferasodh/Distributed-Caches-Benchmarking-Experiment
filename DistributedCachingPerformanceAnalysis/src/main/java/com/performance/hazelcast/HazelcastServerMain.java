package com.performance.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.performance.model.Employee;

import static org.yardstickframework.BenchmarkUtils.println;

/**
 * Created by feras on 6/12/16.
 */
public class HazelcastServerMain {

    public static void main(String [] args){
        // We can make a config for the map
        MapConfig mapConfig = new MapConfig("employees");
        //mapConfig.setAsyncBackupCount(0);
        //mapConfig.setBackupCount(0);
        //mapConfig.setStatisticsEnabled(false);
        
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
       
        
        // No indexing by default
        // TTL = 0 by default
        // Sync operation
        Config hzlCfg = new Config();
        hzlCfg.addMapConfig(mapConfig);

        // To enable the rest APIs
        //cfg.setProperty("hazelcast.rest.enabled", "true");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(hzlCfg);
        IMap<Integer, Employee> imap = instance.getMap("employees");
        imap.addIndex("name", false);
        imap.addIndex("age", false);
        imap.addIndex("organization.name", false);
        
        
        println("I'm going to run forever");
    }

}
