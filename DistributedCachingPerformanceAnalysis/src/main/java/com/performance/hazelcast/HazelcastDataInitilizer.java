package com.performance.hazelcast;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.performance.model.Employee;
import com.performance.model.dataAPI;

import java.util.Map;

/**
 * Created by feras on 6/13/16.
 */
public class HazelcastDataInitilizer {

    private static HazelcastInstance hzClient;
    private static IMap<Integer, Employee> remoteMap;

    public static void main(String [] args){
        String serverIP=args[0];
        int cacheSize=Integer.valueOf(args[1]);

        ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(serverIP + ":5701",
				serverIP + ":5702",
				serverIP + ":5703",
				serverIP + ":5704");
        
        hzClient = com.hazelcast.client.HazelcastClient.newHazelcastClient(clientConfig);
        remoteMap = hzClient.getMap("employees");
        remoteMap.addIndex("name", false);
        remoteMap.addIndex("age", false);
        remoteMap.addIndex("organization.name", false);
        
        initializeMaps(remoteMap,cacheSize);
        System.out.println(remoteMap.get(0));
        System.out.println(remoteMap.get(remoteMap.size() - 1));
        System.out.println("emoteMap.size()=" + remoteMap.size());
    }

    private static void initializeMaps(IMap<Integer, Employee> map,int cacheSize) {

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
