/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.performance.ignite.server;

import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.core.HazelcastInstance;
import com.performance.GeneralArguments;
import com.performance.model.Employee;
import com.performance.model.dataAPI;
import org.apache.ignite.*;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.*;
import org.apache.ignite.internal.util.IgniteUtils;
import org.apache.ignite.internal.util.typedef.F;
import org.apache.ignite.lang.IgniteBiTuple;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.UrlResource;
import org.yardstickframework.BenchmarkConfiguration;
import org.yardstickframework.BenchmarkServer;
import org.yardstickframework.BenchmarkUtils;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.apache.ignite.cache.CacheMemoryMode.OFFHEAP_VALUES;
import static org.yardstickframework.BenchmarkUtils.println;

/**
 * Standalone Ignite node.
 */
public class IgniteNode implements BenchmarkServer {
    /** Grid instance. */
    private Ignite ignite;

    /** Client mode. */
    private boolean clientMode;

    private static int cacheSize = GeneralArguments.cacheSize;
    /** */
    public IgniteNode() {
        // No-op.
    }

    /** */
    public IgniteNode(boolean clientMode) {
        this.clientMode = clientMode;
    }

    /** */
    public IgniteNode(boolean clientMode, Ignite ignite) {
        this.clientMode = clientMode;
        this.ignite = ignite;
    }

    /** {@inheritDoc} */
    @Override public void start(BenchmarkConfiguration cfg) throws Exception {
        IgniteBenchmarkArguments args = new IgniteBenchmarkArguments();

//        IgniteConfiguration c=new IgniteConfiguration();

        BenchmarkUtils.jcommander(cfg.commandLineArguments(), args, "<ignite-node>");

        IgniteBiTuple<IgniteConfiguration, ? extends ApplicationContext> tup = loadConfiguration(args.configuration());

//        TcpDiscoverySpi spi = new TcpDiscoverySpi();
//        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
//        ipFinder.setAddresses(Arrays.asList("127.0.0.1"));
//        spi.setIpFinder(ipFinder);

        IgniteConfiguration c = tup.get1();
        ApplicationContext appCtx = tup.get2();
//        IgniteConfiguration c = new IgniteConfiguration();
//        c.setDiscoverySpi(spi);


//        CacheConfiguration cc=new CacheConfiguration();
//        cc.setEvictionPolicy(new LruEvictionPolicy());
//        cc.setCacheMode(CacheMode.PARTITIONED);
//        c.setLocalHost("127.0.0.1");
//        c.setCacheConfiguration(cc);


//        for (CacheConfiguration cc : c.getCacheConfiguration()) {
//            cc.setEvictionPolicy(new LruEvictionPolicy());
//        }
//        assert c != null;


        boolean cl = args.isClientOnly() && (args.isNearCache() || clientMode);

        if (cl){
                c.setClientMode(true);
                this.clientMode=true;

        }

//        assert appCtx != null;

        for (CacheConfiguration cc : c.getCacheConfiguration()) {
            // IgniteNode can not run in CLIENT_ONLY mode,
            // except the case when it's used inside IgniteAbstractBenchmark.
//            boolean cl = args.isClientOnly() && (args.isNearCache() || clientMode);

//            if (cl){
//                c.setClientMode(true);
//                this.clientMode=true;
//                cc.setEvictionPolicy(new LruEvictionPolicy(50000));
//            }

//            if (args.isNearCache()) {
//                NearCacheConfiguration nearCfg = new NearCacheConfiguration();

//                if (args.getNearCacheSize() != 0)
//                    nearCfg.setNearEvictionPolicy(new LruEvictionPolicy(args.getNearCacheSize()));

//                cc.setNearConfiguration(nearCfg);
//            }

//            cc.setWriteSynchronizationMode(args.syncMode());

//            if (args.orderMode() != null)
//                cc.setAtomicWriteOrderMode(args.orderMode());

//            cc.setBackups(args.backups());

//            if (args.restTcpPort() != 0) {
//                ConnectorConfiguration ccc = new ConnectorConfiguration();
//
//                ccc.setPort(args.restTcpPort());
//
//                if (args.restTcpHost() != null)
//                    ccc.setHost(args.restTcpHost());
//
//                c.setConnectorConfiguration(ccc);
//            }

//            if (args.isOffHeap()) {
//                cc.setOffHeapMaxMemory(0);
//
//                if (args.isOffheapValues())
//                    cc.setMemoryMode(OFFHEAP_VALUES);
//                else
//
//            }


//            cc.setReadThrough(args.isStoreEnabled());

//            cc.setWriteThrough(args.isStoreEnabled());

//            cc.setWriteBehindEnabled(args.isWriteBehind());
//            cc.setEvictionPolicy(new LruEvictionPolicy());
        }

        TcpCommunicationSpi commSpi = (TcpCommunicationSpi)c.getCommunicationSpi();

        if (commSpi == null)
            commSpi = new TcpCommunicationSpi();

        c.setCommunicationSpi(commSpi);


//        TcpCommunicationSpi commSpi = new TcpCommunicationSpi();//(TcpCommunicationSpi)c.getCommunicationSpi();

//        commSpi.setLocalPort(47501);
//        commSpi.setLocalAddress("127.0.0.1");
//        commSpi.setLocalPortRange(50);
//
//        if (commSpi == null)
//            commSpi = new TcpCommunicationSpi();

//        c.setCommunicationSpi(commSpi);
//        final Map<InetSocketAddress, ? extends Collection<InetSocketAddress>> mp = F.asMap(
//                new InetSocketAddress("127.0.0.1", 47501), F.asList(new InetSocketAddress("127.0.0.1", 47501)),
//                new InetSocketAddress("127.0.0.1", 47503), F.asList(new InetSocketAddress("127.0.0.1", 47503))
//        );
//
//        c.setAddressResolver(new AddressResolver() {
//            @Override public Collection<InetSocketAddress> getExternalAddresses(InetSocketAddress addr) {
//                return mp.get(addr);
//            }
//        });


        ignite = IgniteSpring.start(c, appCtx);

        if(!this.clientMode){
            initializeMaps(ignite);
        }

        println("I'm going to run forever");
    }

    /**
     * @param springCfgPath Spring configuration file path.
     * @return Tuple with grid configuration and Spring application context.
     * @throws Exception If failed.
     */
    private static IgniteBiTuple<IgniteConfiguration, ? extends ApplicationContext> loadConfiguration(String springCfgPath)
        throws Exception {
        URL url;

        try {
            url = new URL(springCfgPath);
        }
        catch (MalformedURLException e) {
            url = IgniteUtils.resolveIgniteUrl(springCfgPath);

            if (url == null)
                throw new IgniteCheckedException("Spring XML configuration path is invalid: " + springCfgPath +
                    ". Note that this path should be either absolute or a relative local file system path, " +
                    "relative to META-INF in classpath or valid URL to IGNITE_HOME.", e);
        }

        GenericApplicationContext springCtx;

        try {
            springCtx = new GenericApplicationContext();

            new XmlBeanDefinitionReader(springCtx).loadBeanDefinitions(new UrlResource(url));

            springCtx.refresh();
        }
        catch (BeansException e) {
            throw new Exception("Failed to instantiate Spring XML application context [springUrl=" +
                url + ", err=" + e.getMessage() + ']', e);
        }

        Map<String, IgniteConfiguration> cfgMap;

        try {
            cfgMap = springCtx.getBeansOfType(IgniteConfiguration.class);
        }
        catch (BeansException e) {
            throw new Exception("Failed to instantiate bean [type=" + IgniteConfiguration.class + ", err=" +
                e.getMessage() + ']', e);
        }

        if (cfgMap == null || cfgMap.isEmpty())
            throw new Exception("Failed to find ignite configuration in: " + url);

        return new IgniteBiTuple<>(cfgMap.values().iterator().next(), springCtx);
    }

    /** {@inheritDoc} */
    @Override public void stop() throws Exception {
        Ignition.stopAll(true);
        println("I'm stopped now!!!");
    }

    /** {@inheritDoc} */
    @Override public String usage() {
        return BenchmarkUtils.usage(new IgniteBenchmarkArguments());
    }

    /**
     * @return Ignite.
     */
    public Ignite ignite() {
        return ignite;
    }

    private static void initializeMaps(Ignite instance) {
        println("Initialize Maps");

        CacheConfiguration<Integer, Employee> employeesCfg = new CacheConfiguration<>("employees");

        employeesCfg.setName("employees");
        employeesCfg.setIndexedTypes(Integer.class, Employee.class);
        IgniteCache map = instance.getOrCreateCache(employeesCfg);

        if(map.randomEntry()==null) {
            dataAPI dataApi = new dataAPI();

            for (int i = 0; i < cacheSize; i++) {
                map.put(i, dataApi.getEmployee(i));
            }

            System.out.println("Initialization done!");
            println("done initialize Maps");
        }
        println("Finished initialize Maps");
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
