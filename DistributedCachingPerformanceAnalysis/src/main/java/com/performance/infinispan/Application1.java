/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performance.infinispan;

/**
 *
 * @author Fayez
 */

import java.util.List;
 
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
 
import org.infinispan.query.dsl.*;
 
  
public class Application1 {
 
   public void testQuery() {
      System.out.println("Demonstrating basic Query usage of Infinispan.");
       
      Configuration infinispanConfiguration = new ConfigurationBuilder()
      
      .build();
 
      DefaultCacheManager cacheManager = new DefaultCacheManager(infinispanConfiguration);
      
      Cache<String, Book> cache = cacheManager.getCache();
      
      cache.put("b1", new Book("hobbit","a","editor"));
      cache.put("b2", new Book("the hobbit","author","editor"));
      cache.put("b3", new Book("palestine history","author","editor"));
      cache.put("b4", new Book("engine","author","editor"));
      cache.put("b5", new Book("engine","author","editor"));
      // get the DSL query factory from the cache, to be used for constructing the Query object:
QueryFactory qf = org.infinispan.query.Search.getQueryFactory(cache);

// create a query for all the books that have a title which contains the word "engine":
org.infinispan.query.dsl.Query query = qf.from(Book.class)
      .having("title").like("%engine%")
      .toBuilder().build();

// get the results:
List<Book> list = query.list();
      System.out.println(list);
   }
 
  
   public static void main(String[] args) throws Exception {
      Application1 a = new Application1();
      a.testQuery();
 
      System.out.println("Sample complete.");
   }
 
   
}