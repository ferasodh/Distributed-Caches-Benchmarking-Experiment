package com.performance.infinispan.server;


import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class SimpleInfinispanServer {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String[] commands = {
				"standalone.bat -c clustered.xml -Djboss.node.name=nodeA -Djboss.socket.binding.port-offset=100",
				"standalone.bat -c clustered.xml -Djboss.node.name=nodeB -Djboss.socket.binding.port-offset=200",
				"standalone.bat -c clustered.xml -Djboss.node.name=nodeC -Djboss.socket.binding.port-offset=300",
				"standalone.bat -c clustered.xml -Djboss.node.name=nodeD -Djboss.socket.binding.port-offset=400"
		};
		
		for (String string : commands) {
			
			Thread thread = new Thread() {
			    public void run(){
					String line = "cmd.exe /C C:\\Users\\TechZone\\Desktop\\infinispan-server-8.2.0.Final\\bin\\" + string;
					CommandLine cmdLine = CommandLine.parse(line);
					DefaultExecutor executor = new DefaultExecutor();
					int exitValue = -1;
					try {
						exitValue = executor.execute(cmdLine);
					} catch (ExecuteException e) {
					} catch (IOException e) {
					}
					
					
			    }
			  };

			  thread.start();
			  System.out.println(thread.getId() + " is started!");
			  Thread.sleep(60000);
		}
		
		
		int x = 010;
		
		System.out.println(x);
		
		// Intitliaze the default cache from a client!
	}

}
