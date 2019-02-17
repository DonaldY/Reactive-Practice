package com.donaldy.reactive.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Flux 示例
 */
public class FluxDemo {

    public static void main(String[] args) throws InterruptedException {

        // println("hello world");

        println("运行。。。");
        // 可看到是同步线程， 但非阻塞
        Flux.just("A", "B", "C") // 发布 A -> B -> C
                .publishOn(Schedulers.elastic())       // 线程池切换
                .subscribe(FluxDemo::println,          // 数据消费
                        FluxDemo::println,             // 异常处理
                        () -> {println("完成操作！");},  // 完成回调
                        subscription -> {              // 背压操作
                            subscription.request(1); // l 是 请求数量
                        });

        /**
         * 有时候没有打印出
         *
         * 因为 主线程退出了 相应的其子线程也会退出
         *
         * 典型的并发问题，异步问题。
         *
         * 只要阻塞一下
         */
        // Thread.sleep(1000L);
    }

    private static void println(Object object) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[线程：" + threadName + "] " + object);
    }
}
