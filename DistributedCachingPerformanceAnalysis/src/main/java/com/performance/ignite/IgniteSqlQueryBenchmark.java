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

import com.performance.GeneralArguments;
import com.performance.model.Employee;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.SqlQuery;

import javax.cache.Cache;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import static org.yardstickframework.BenchmarkUtils.println;

/**
 * Ignite benchmark that performs query operations.
 */
public class IgniteSqlQueryBenchmark extends IgniteCacheAbstractBenchmark<Integer, Object> {

    /** {@inheritDoc} */
    @Override public boolean test(Map<Object, Object> ctx) throws Exception {
        println("I'm the test");
        println("Args:" + args.toString());

        Random random = new Random();
        int randomAge = 3;//random.nextInt(GeneralArguments.agesBound);
        println("randomAge = " + randomAge);

        Collection<Cache.Entry<Integer, Object>> entries = executeQuery(randomAge);

        println("I finished with employees of size = " + entries.size());
        return true;
    }

    /**
     * @return Query result.
     * @throws Exception If failed.
     */
    private Collection<Cache.Entry<Integer, Object>> executeQuery(int randomAge) throws Exception {
        SqlQuery qry = new SqlQuery(Employee.class, "age > ? ");

        qry.setArgs(randomAge);

        return cache.query(qry).getAll();
    }

    /** {@inheritDoc} */
    @Override protected IgniteCache<Integer, Object> cache() {
        return ignite().cache("employees");
    }
}
