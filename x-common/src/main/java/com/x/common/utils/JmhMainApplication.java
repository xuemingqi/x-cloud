package com.x.common.utils;

/**
 * @author : xuemingqi
 * @since : 2023/9/22 13:36
 */

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput) // 吞吐量
@OutputTimeUnit(TimeUnit.SECONDS) // 结果所使用的时间单位
@State(Scope.Thread) // 每个测试线程分配一个实例
@Fork(2) // Fork进行的数目
@Warmup(iterations = 1) // 先预热1轮
@Measurement(iterations = 2) // 进行2轮测试
public class JmhMainApplication {

    @Setup(Level.Trial)
    public void init() {

    }

    @Benchmark
    public void linkList() {

    }

    @Benchmark
    public void arrayList() {

    }


    @TearDown(Level.Trial)
    public void arrayRemove() {

    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(JmhMainApplication.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
