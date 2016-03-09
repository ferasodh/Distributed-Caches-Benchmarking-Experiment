package com.performance;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.performance.hazelcast.HazelcastBenchmark;

/**
 * Here we can include the benchmarks as needed to run them all
 * 
 * @author Haytham Salhi
 *
 */
public class BenchmarkRunner {
	
	public static void main(String[] args) throws RunnerException {
		  Options opt = new OptionsBuilder()
		  .include(HazelcastBenchmark.class.getSimpleName())
		  //.include(MyBenchmark.class.getSimpleName())
		  .resultFormat(ResultFormatType.CSV)
		  .result("output.csv")
		  .forks(1)
		  .build();
		
		  new Runner(opt).run();
		}
}
