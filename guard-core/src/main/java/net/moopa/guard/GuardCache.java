package net.moopa.guard;

import net.moopa.guard.model.account.Account;
import net.moopa.guard.model.role.Role;
import net.moopa.guard.token.AuthorizeToken;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.omg.CORBA.StringHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moopa on 18/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class GuardCache {
    private static Logger logger = LoggerFactory.getLogger(GuardCache.class);
    private static CacheManager accountCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("accountCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Account.class,
                            ResourcePoolsBuilder.heap(100))
                            .build())
            .build(true);


    private static CacheManager roleCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("roleCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Role.class,
                            ResourcePoolsBuilder.heap(100))
                            .build())
            .build(true);

    private static CacheManager tokenCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("tokenCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, AuthorizeToken.class,
                            ResourcePoolsBuilder.heap(10000))
                            .build())
            .build(true);


    //roleCache的key是roleName
    public Cache<String,Role> roleCache = roleCacheManager.getCache("roleCache", String.class,Role.class);

    //token是tokenname
    public Cache<String,Account> accountCache = accountCacheManager.getCache("accountCache",String.class,Account.class);

    //account的key是loginname
    public Cache<String,AuthorizeToken> tokenCache = tokenCacheManager.getCache("tokenCache",String.class,AuthorizeToken.class);


    protected Map<Integer,Role> roleIdMapRolename = new HashMap<Integer, Role>();

    protected GuardCache(){
        logger.info("---------- init guard cache ----------");
    }

    public AuthorizeToken getAuthorizeTokenByTokenName(String jwt){
        return tokenCache.get(jwt);
    }

    public void putAuthorizeToken(String jwt,AuthorizeToken authorizeToken){
        tokenCache.put(jwt,authorizeToken);
    }

    public Role getRolenameByRoleId(int rid){
        Role result = roleIdMapRolename.get(rid);
        if(result == null){
            logger.error("No rolename map rold id : "+rid);
        }
        return result;
    }

    public Account getAccount(String loginname){
        return accountCache.get(loginname);
    }

    public void putAccount(String loginname,Account account){
        accountCache.put(loginname,account);
    }

    public Role getRole(String rolename){
        return roleCache.get(rolename);
    }

    public void putRole(String rolename,Role role){
        roleCache.put(rolename,role);
    }

}
