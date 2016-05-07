package com.performance.hazelcast;

import java.util.Map;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.performance.GeneralArguments;
import com.performance.model.Employee;
import com.performance.model.dataAPI;

public class Test {
	
	private static HazelcastInstance hzClient;
	private static IMap<Integer, Employee> remoteMap;
	
	private static String serverIP = GeneralArguments.serverIP;

	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		//clientConfig.getNetworkConfig().addAddress(serverIP + ":5701", serverIP + ":5702", serverIP + ":5703", serverIP + ":5704");
		
		clientConfig.getNetworkConfig().addAddress("10.0.0.1:5701");
		
		hzClient = HazelcastClient.newHazelcastClient(clientConfig);
		remoteMap = hzClient.getMap("employees");
		
		System.out.println(remoteMap.get(0));

	}

}
