package com.hjk.wangpan.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

// @since 3.1  可以标注在方法上、类上  下同
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
    // 缓存名称  可以写多个，key的真正组成，以cacheName为前缀，多个就会有多个key产生
    @AliasFor("cacheNames")
    String[] value() default {};

    @AliasFor("value")
    String[] cacheNames() default {};

    // 支持写SpEL，切可以使用#root，#参数名”或者“#p参数index”
    //详情去 https://blog.csdn.net/dalong_bamboo/article/details/103844076
    String key() default "";

    // Mutually exclusive：它和key属性互相排斥。请只使用一个，直接写bean的名字就可以
    String keyGenerator() default "";

    //用于选择使用哪个cacheManager
    String cacheManager() default "";

    //用户定义如何处理缓存，实现 org.springframework.cache.interceptor.CacheResolver接口
    String cacheResolver() default "";

    // 表示在哪种情况下才缓存结果，可使用SpEL，可以使用#root。  只有true时，才会作用在这个方法上
    String condition() default "";

    // 表示在哪种情况下不缓存结果，可以写SpEL #root，并且可以使用#result拿到方法返回值    经典值#result == null
    String unless() default "";

    // true：表示强制同步执行。（若多个线程试图为**同一个键**加载值，以同步的方式来进行目标方法的调用）
    // 同步的好处是：后一个线程会读取到前一个缓存的缓存数据，不用再查库了~~~
    // 默认是false，不开启同步one by one的
    // @since 4.3  注意是sync而不是Async
    // 它的解析依赖于Spring4.3提供的Cache.get(Object key, Callable<T> valueLoader);方法
    boolean sync() default false;
}


