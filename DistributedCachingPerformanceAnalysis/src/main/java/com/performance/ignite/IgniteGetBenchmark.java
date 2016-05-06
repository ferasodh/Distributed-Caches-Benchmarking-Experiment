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

package com.performance.ignite;

import com.performance.model.Employee;
import org.apache.ignite.IgniteCache;

import java.util.Map;
import java.util.Random;

import static org.yardstickframework.BenchmarkUtils.println;

/**
 * Ignite benchmark that performs get operations.
 */
public class IgniteGetBenchmark extends IgniteCacheAbstractBenchmark<Integer, Object> {
    /** {@inheritDoc} */
    @Override public boolean test(Map<Object, Object> ctx) throws Exception {
        println("I'm the test");
        println("Args:" + args.toString());

        Random random = new Random();
        int key = nextRandom(args.range());


        Employee emp = (Employee) cache.get(key);

        println("I finished with emp = " + emp);
        return true;
    }

    /** {@inheritDoc} */
    @Override protected IgniteCache<Integer, Object> cache() {
        return ignite().cache("atomic");
    }
}