package com.t3h.ecommerce.config.cache;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;

@EnableCaching
@Configuration
@Slf4j
public class CacheConfig {

    @Bean
    public JCacheCacheManager cacheManager(){
        CachingProvider cacheProvider = Caching.getCachingProvider();
        try {
            CacheManager cacheManager = cacheProvider.getCacheManager(
                    getClass().getResource("/ehcache.xml").toURI(),
                    getClass().getClassLoader());
            return new JCacheCacheManager(cacheManager);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("create bean cache error");
        }
        return null;
    }


}
