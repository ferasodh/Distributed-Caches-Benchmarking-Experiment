# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###
This repostitory contains the implementation of a benchmarking/performance analysis experiment on distributed cache systems, Hazelcast and Infinispan, using Yardstick framework. 

The main contribution is that developing scripts on top of yardstick in order to perform benhmarking as the number of clients (each with a its own connection) varies.

### How do I get set up? ###

#### Environment Setup ####

We need to setup two PCs with the specifications described in the design chapter as follows:

* Run the first PC that represents the clients.
* Run the second PC that represents the server running the cache cluster.
* Set up the JDK environment on the 2 PCs.
* Install eclipse for Java EE on both machines.
* Checkout the project from this repo.
* Choose the master_yardstick branch.
* Set up a point to point network with a direct cross cable. Assign the IPaddresses 10.0.0.2/8 and 10.0.0.1/8 to both PCs, repsectively.
* Open the following ports on the server machine: 5701 to 5705 for hazelcast servers and 11222, 11322, 11422, and 11522 for infinispan servers.
* A configuration class called GeneralArguments contains main configs including the server IP, data size, and ages bound used in custom queries.

 
#### Hazelcast Setup ####

In this section, we show the steps to run the benchmarks for hazelcast. First we need to set up the server, then we set up the clients.

##### Servers Running

After checking out the project as described earlier, you should be able to build the project normally. To run the servers, follow these steps:

* Specify the data size in GeneralArguments class to an initial value.
* Build the project using maven plugin with the targets clean package.
* Open the command line interface (cmd) and change the directory to where the project exists.
* Issue this command: bin\benchmark-manual-servers-start.bat. This command will run the hazelcast servers (4 nodes), and a distributed * cache will be created and initialized with the data.
* After running the clients as described next, kill the servers and repeat from step 1 to set the data size a new data size value.

##### Clients Running

After making the servers up and running, we need now to run the benchmarks as follows:

* Build the project with maven with the targets clean package.
* Open the bin\benchmark-bootstrap.sh file, then go to line 86, and then change the -Xms and -Xmx values to 128m and 256m, respectively.
* Open the bin\benchmark-manual-drivers-start.sh, then go to lines 91 and 100, and then append %N to %H%M%S string. 
* Open the terminal and change the directory to where the project exists.
* Issue the following command: ./run_clients2.sh Hazelcast 5000. We pass to this command the system name (i.e. Hazelcast in this case) and the data size of the distributed cache. This command will run the benchmarks for different types of queries (get, custom, custom-complex) and for different number of clients (1, 4, 16, ... etc). It will trigger the monitor.sh script as well as handling the creation and movement of result files.
* Repeat from step 5, restart the servers with new data size, and change the data size to another data size.


#### Infinispan Setup ####

In this section, we show the steps to run the benchmarks for Infinispan. First we need to set up the server, then we set up the clients.

##### Servers Running

Running Infinispan servers is somewhat different. You need to install the Infinispan server runtime from their web site (http://infinispan.org/download/). The version used in this experiment is 8.2.2.final. To run the servers, follow these steps:

* Open the cmd and change directory to where the Infinispan server folder exists.
* Go to the bin folder and issue the following commands, each in separate cmd:
standalone.bat -c clustered.xml -Djboss.node.name=nodeA 
standalone.bat -c clustered.xml -Djboss.node.name=nodeB -Djboss.socket.binding.port-offset=100
standalone.bat -c clustered.xml -Djboss.node.name=nodeC -Djboss.socket.binding.port-offset=200
standalone.bat -c clustered.xml -Djboss.node.name=nodeD -Djboss.socket.binding.port-offset=300
* Specify the data size in GeneralArguments class to initial data size.
* To initialize the data, we have two programs: one for get query and the other for custom queries.
* When benchmarking get query, run the class ServerSimpleDataInitializer. (It needs to restart servers)
* When benchmarking custom queries, run the class ServerCustomDataInitializer. (It needs to restart servers)
* After running the clients as described next, kill the servers and repeat from step 1 but with data size of another data size. 

##### Clients Running

After making the servers up and running, we need now to run the benchmarks as follows:

* Build the project with maven with the targets clean package.
* Open the bin\benchmark-bootstrap.sh file, then go to line 86, and then change the -Xms and -Xmx values to 128m and 256m, respectively.
* Open the bin\benchmark-manual-drivers-start.sh, then go to lines 91 and 100, and then append %N to %H%M%S string. 
* Open the terminal and change the directory to where the project exists.
* We have two scripts in the client side: 
** When benchmarking get query: Issue the following command:  ./run_clients_infinispan_get.sh Infinispan 5000. We pass to this command the system name (i.e. Infinispan in this case) and the data size of the distributed cache. This command will run the benchmark for get query and for different number of clients (1, 4, 16). It will trigger the monitor.sh script as well as handling the creation and movement of result files.
** When benchamrking custom queries: Issue the following command:
./run_clients_infinispan_custom.sh Infinispan 5000. This command will run the benchmarks for custom and custom-complex queries and for different number of clients (1, 4, 16). It will trigger the monitor.sh script as well as handling the creation and movement of result files.
* Repeat from step 5, restart the servers with new data size, and change the data size to another data size.


### Contribution guidelines ###

* Developing scripts on top of yardstick in order to perform benhmarking as the number of clients (each with a its own connection) varies.
* Performing a controlled performance analysis experiment on Hazelcast and Yardstick.

### Who do I talk to? ###

* Haytham Salhi (hsalhi89@gmail.com)
* Feras Odeh (ferasodh@gmail.com)
