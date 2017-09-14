package net.moopa3376.guard.cache;

import net.moopa3376.guard.model.account.Account;
import net.moopa3376.guard.model.role.Role;
import net.moopa3376.guard.token.AuthorizedToken;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Moopa on 18/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class GuardCache {
    private static Logger logger = LoggerFactory.getLogger(GuardCache.class);
    private static CacheManager accountCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("accountCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Account.class,
                            ResourcePoolsBuilder.heap(100))).build();


    private static CacheManager roleCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("roleCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, Role.class,
                            ResourcePoolsBuilder.heap(100))).build();

    private static CacheManager roleCacheManager1 = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("roleCacheNameKey",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Role.class,
                            ResourcePoolsBuilder.heap(100))).build();


    private static CacheManager tokenCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("tokenCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, AuthorizedToken.class,
                            ResourcePoolsBuilder.heap(10000))).build();



    //roleCache的key是roleName
    public Cache<Long,Role> roleCache = null;
    public Cache<String,Role> roleCache1 = null;

    //account的key是token
    public Cache<String,Account> accountCache = null;

    //token是tokenname
    public Cache<String,AuthorizedToken> tokenCache = null;

    protected GuardCache(){
        logger.info("---------- init guard cache ----------");
        accountCacheManager.init();
        roleCacheManager.init();
        roleCacheManager1.init();
        tokenCacheManager.init();

        roleCache = roleCacheManager.getCache("roleCache", Long.class,Role.class);
        roleCache1 = roleCacheManager1.getCache("roleCacheNameKey", String.class,Role.class);
        accountCache = accountCacheManager.getCache("accountCache",String.class,Account.class);
        tokenCache = tokenCacheManager.getCache("tokenCache",String.class,AuthorizedToken.class);
    }
}
