package com.x.config.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author xuemingqi
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Slf4j
public class RedisUtil {

    private final RedissonClient redissonClient;

    public RedisUtil(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key) {
        RBucket<T> rBucket = getRBucket(key);
        return rBucket.get();
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public <T> void set(String key, T value) {
        getRBucket(key).set(value);
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key        键
     * @param value      值
     * @param timeoutSec 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public <T> void set(String key, T value, long timeoutSec, TimeUnit timeUnit) {
        getRBucket(key).set(value, timeoutSec, timeUnit);
    }

    /**
     * 设置指定 key 的值，并返回 key 的旧值
     */
    public <T> T getAndSet(String key, T value) {
        RBucket<T> rBucket = getRBucket(key);
        return rBucket.getAndSet(value);
    }

    /**
     * 设置指定 key 的值，并返回 key 的旧值，并设置键的有效期
     */
    public <T> T getAndSet(String key, T value, long timeToLive, TimeUnit timeUnit) {
        RBucket<T> rBucket = getRBucket(key);
        return rBucket.getAndSet(value, timeToLive, timeUnit);
    }


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return getRBucket(key).remainTimeToLive() / 1000;
    }


    /**
     * 指定缓存失效时间
     *
     * @param key        键
     * @param timeoutSec 时间(秒)
     */
    public void expired(String key, long timeoutSec) {
        expired(key, timeoutSec, TimeUnit.SECONDS);
    }


    /**
     * 指定缓存失效时间
     *
     * @param key        键
     * @param timeoutSec 时间(秒)
     */
    public void expired(String key, long timeoutSec, TimeUnit unit) {
        getRBucket(key).expire(timeoutSec, unit);
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean exists(String key) {
        return getRBucket(key).isExists();
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     * @return 数量
     */
    public boolean del(String key) {
        return getRBucket(key).delete();
    }


    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     * @return 数量
     */
    public long del(String... keys) {
        return getRKeys().delete(keys);
    }


    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public long incr(String key, long delta) {
        return getRAtomicLong(key).addAndGet(delta);
    }


    /**
     * HashGet
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     */
    public <K, V> V hGet(String key, K hashKey) {
        RMapCache<K, V> rMapCache = getRMapCache(key);
        return rMapCache.get(hashKey);
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     */
    public <k, v> void hSet(String key, k hashKey, v value) {
        getRMapCache(key).put(hashKey, value);
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key        键
     * @param hashKey    项
     * @param value      值
     * @param timeoutSec 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public <k, v> void hSet(String key, k hashKey, v value, long timeoutSec) {
        getRMapCache(key).put(hashKey, value, timeoutSec, TimeUnit.SECONDS);
    }


    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public <K, V> Map<K, V> hMGet(String key) {
        RMapCache<K, V> rMapCache = getRMapCache(key);
        return rMapCache.readAllMap();
    }


    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public <K, V> void hMSet(String key, Map<? extends K, ? extends V> map) {
        getRMapCache(key).putAll(map);
    }


    /**
     * HashSet 并设置时间
     *
     * @param key        键
     * @param map        对应多个键值
     * @param timeoutSec 时间(秒)
     */
    public <K, V> void hMSet(String key, Map<? extends K, ? extends V> map, long timeoutSec) {
        RMapCache<K, V> rMapCache = getRMapCache(key);
        rMapCache.putAll(map, timeoutSec, TimeUnit.SECONDS);
    }


    /**
     * 删除hash表中的值
     *
     * @param key      键 不能为null
     * @param hashKeys 项 可以使多个 不能为null
     */
    public long hDel(String key, Object... hashKeys) {
        return getRMapCache(key).fastRemove(hashKeys);
    }


    /**
     * 判断hash表中是否有该项的值
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hExists(String key, String hashKey) {
        return getRMapCache(key).containsKey(hashKey);
    }


    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key     键
     * @param hashKey 项
     * @param delta   要增加几(大于0)
     */
    public long hIncr(String key, String hashKey, long delta) {
        return (long) getRMapCache(key).addAndGet(hashKey, delta);
    }


    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key     键
     * @param hashKey 项
     * @param delta   要增加几(大于0)
     */
    public double hIncr(String key, String hashKey, double delta) {
        return (double) getRMapCache(key).addAndGet(hashKey, delta);
    }


    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public <V> Set<V> sGet(String key) {
        RSet<V> rSet = getRSet(key);
        return rSet.readAll();
    }


    /**
     * 根据key获取Set中的值
     *
     * @param key 键
     */
    public <V> V sPop(String key) {
        RSet<V> rSet = getRSet(key);
        return rSet.removeRandom();
    }


    /**
     * 根据key获取Set中的值
     *
     * @param key   键
     * @param count 数量
     */
    public <V> Set<V> sPop(String key, int count) {
        RSet<V> rSet = getRSet(key);
        return rSet.removeRandom(count);
    }


    /**
     * 根据key获取Set中的值
     *
     * @param key 键
     */
    public <V> V sRandom(String key) {
        RSet<V> rSet = getRSet(key);
        return rSet.random();
    }


    /**
     * 根据key获取Set中的值
     *
     * @param key   键
     * @param count 数量
     */
    public <V> Set<V> sRandom(String key, int count) {
        RSet<V> rSet = getRSet(key);
        return rSet.random(count);
    }


    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public <E> boolean sAdd(String key, E values) {
        RSet<E> rSet = getRSet(key);
        return rSet.add(values);
    }


    /**
     * 将set数据放入缓存
     *
     * @param key        键
     * @param timeoutSec 时间(秒)
     * @param values     值 可以是多个
     * @return 成功个数
     */
    public <E> boolean sAddExpire(String key, long timeoutSec, E values) {
        RSet<E> rSet = getRSet(key);
        rSet.add(values);
        return getRSet(key).expire(timeoutSec, TimeUnit.SECONDS);
    }


    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */

    public boolean sDel(String key, Object values) {
        return getRSet(key).remove(values);
    }


    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sExists(String key, Object value) {
        return getRSet(key).contains(value);
    }


    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public int sSize(String key) {
        return getRSet(key).size();
    }


    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1 代表所有值
     */
    public <T> List<T> lRange(String key, int start, int end) {
        RList<T> rList = getRList(key);
        return rList.range(start, end);
    }


    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public <T> T lIndex(String key, int index) {
        RList<T> rList = getRList(key);
        return rList.get(index);
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @param value 值
     */
    public <T> void lSet(String key, int index, T value) {
        RList<T> rList = getRList(key);
        rList.add(index, value);
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @param value 值
     */
    public <T> void lSet(String key, int index, T value, long timeoutSec) {
        RList<T> rList = getRList(key);
        rList.add(index, value);
        rList.expire(timeoutSec, TimeUnit.SECONDS);
    }


    /**
     * trim list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1 代表所有值
     */
    public void ltrim(String key, int start, int end) {
        getRList(key).trim(start, end);
    }


    public <T> T lPopLeft(String key) {
        RDeque<T> rDeque = getRRDeque(key);
        return rDeque.removeFirst();
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <T> boolean lPushLeft(String key, T value) {
        RDeque<T> rDeque = getRRDeque(key);
        return rDeque.add(value);
    }


    public <T> void lPushLeft(String key, T value, long timeoutSec) {
        RDeque<T> rDeque = getRRDeque(key);
        rDeque.addFirst(value);
        rDeque.expire(timeoutSec, TimeUnit.SECONDS);
    }


    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     */
    public <T> boolean lPushLeftAll(String key, List<T> values) {
        RDeque<T> rDeque = getRRDeque(key);
        return rDeque.addAll(values);
    }


    public <T> T lPopRight(String key) {
        RDeque<T> rDeque = getRRDeque(key);
        return rDeque.removeLast();
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <T> void lPushRight(String key, T value) {
        RDeque<T> rDeque = getRRDeque(key);
        rDeque.addLast(value);
    }


    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public <V> boolean lDel(String key, int count, V value) {
        RList<V> rList = getRList(key);
        return rList.remove(value, count);
    }


    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lSize(String key) {
        return getRList(key).size();
    }


    /**
     * 同步锁
     *
     * @param key        key
     * @param timeoutSec 锁超时时间
     * @param waitTime   加锁等待时间
     */
    public boolean lock(String key, long timeoutSec, long waitTime) {
        RLock lock = getRlock(key);
        try {
            return lock.tryLock(waitTime, timeoutSec, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("redisson lock error", e);
            return false;
        }
    }


    /**
     * 同步锁
     *
     * @param key        key
     * @param timeoutSec 锁超时时间
     */
    public boolean lock(String key, long timeoutSec) {
        RLock lock = getRlock(key);
        try {
            return lock.tryLock(timeoutSec, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("redisson lock error", e);
            return false;
        }
    }


    /**
     * 释放同步锁
     *
     * @param key key
     */
    public void unLock(String key) {
        RLock lock = getRlock(key);
        if (lock.isLocked()) {
            lock.unlock();
        }
    }

    /**
     * 添加定时任务
     *
     * @param key      任务key
     * @param runnable 执行方法
     * @param delay    延迟时间
     * @param period   调度周期
     */
    public RScheduledFuture<?> schedule(String key, Runnable runnable, long delay, long period) {
        return getRScheduledExecutorService(key).scheduleAtFixedRate(runnable, delay, period, TimeUnit.SECONDS);
    }

    /**
     * 添加任务
     *
     * @param key      任务key
     * @param callable 执行方法
     * @param delay    延迟时间
     */
    public <T> RScheduledFuture<T> schedule(String key, Callable<T> callable, long delay) {
        return getRScheduledExecutorService(key).schedule(callable, delay, TimeUnit.SECONDS);
    }

    /**
     * 添加任务
     *
     * @param key      任务key
     * @param runnable 执行方法
     */
    public Future<?> submit(String key, Runnable runnable) {
        return getRExecutorService(key).submit(runnable);
    }

    /**
     * 限流
     *
     * @param key    限流key
     * @param counts 令牌数
     * @param unit   计数时间单位
     */
    public boolean tryAcquire(String key, long counts, RateIntervalUnit unit) {
        RRateLimiter limiter = getRRateLimiter(key);
        limiter.trySetRate(RateType.OVERALL, counts, 1, unit);
        return limiter.tryAcquire();
    }

    /**
     * 通用对象桶
     */
    private <T> RBucket<T> getRBucket(String key) {
        return redissonClient.getBucket(key);
    }

    /**
     * 原子整长形
     */
    private RAtomicLong getRAtomicLong(String key) {
        return redissonClient.getAtomicLong(key);
    }

    /**
     * 对象
     */
    private RKeys getRKeys() {
        return redissonClient.getKeys();
    }

    /**
     * 批量
     */
    private RBatch getRBatch() {
        return redissonClient.createBatch();
    }

    /**
     * 映射
     */
    private <K, V> RMap<K, V> getRMap(String key) {
        return redissonClient.getMap(key);
    }

    /**
     * 集
     */
    private <V> RSet<V> getRSet(String key) {
        return redissonClient.getSet(key);
    }

    /**
     * 列表
     */
    private <V> RList<V> getRList(String key) {
        return redissonClient.getList(key);
    }

    /**
     * 分布式淘汰机制map
     */
    private <K, V> RMapCache<K, V> getRMapCache(String key) {
        return redissonClient.getMapCache(key);
    }

    /**
     * 分布式淘汰机制set
     */
    private <V> RSetCache<V> getRSetCache(String key) {
        return redissonClient.getSetCache(key);
    }

    /**
     * 双端队列
     */
    private <V> RDeque<V> getRRDeque(String key) {
        return redissonClient.getDeque(key);
    }

    /**
     * 锁
     */
    private RLock getRlock(String key) {
        return redissonClient.getLock(key);
    }

    /**
     * 限流
     */
    private RRateLimiter getRRateLimiter(String key) {
        return redissonClient.getRateLimiter(key);
    }

    /**
     * 定时任务
     */
    private RScheduledExecutorService getRScheduledExecutorService(String key) {
        return redissonClient.getExecutorService(key);
    }

    /**
     * 定时任务
     */
    private RExecutorService getRExecutorService(String key) {
        return redissonClient.getExecutorService(key);
    }

}
