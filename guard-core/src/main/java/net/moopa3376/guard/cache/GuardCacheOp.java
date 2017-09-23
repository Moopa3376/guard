package net.moopa3376.guard.cache;

import net.moopa3376.guard.Guard;
import net.moopa3376.guard.model.account.Account;
import net.moopa3376.guard.model.permission.Permission;
import net.moopa3376.guard.model.role.Role;
import net.moopa3376.guard.service.IGuardService;
import net.moopa3376.guard.token.AuthorizedToken;

import java.util.List;

/**
 * Created by Moopa on 18/07/2017.
 * blog: moopa.net
 *
 * Guard用户相关信息、token缓存获取
 *
 * @autuor : Moopa
 */
public class GuardCacheOp {
    public static IGuardService guardService = Guard.guardService;

    //缓存
    private static GuardCache cache = new GuardCache();

    //初始化
    public static void init(){
        //初始化缓存,读入角色和权限数据
        List<Role> roles = guardService.leadIntoRole();
        List<Permission> permissions = guardService.leadIntoPermission();

        //匹配角色和权限
        guardService.matchRoleAndPermission(roles,permissions);

        //加入缓存
        for(Role r : roles){
            cache.roleCache.put(r.getId(),r);
            cache.roleCache1.put(r.getName(),r);
        }

    }


    /**
     * 缓存相关操作
     */
    public static Account getAccount(String userId){
        return cache.accountCache.get(userId);
    }

    public static void putToken(AuthorizedToken token){
        cache.tokenCache.put(token.getJwtToken(),token);
    }
    public static AuthorizedToken getToken(String token){
        return cache.tokenCache.get(token);
    }
    public static void removeToken(String token){
        cache.tokenCache.remove(token);
    }
    public static boolean isTokenExist(String tokenname){
        return cache.tokenCache.containsKey(tokenname);
    }


    public static Role getRoleById(Long id){
        return cache.roleCache.get(id);
    }
    public static Role getRoleByRolename(String rolename){
        return cache.roleCache1.get(rolename);
    }
    public static void putRole(Role role){
        cache.roleCache.put(role.getId(),role);
        cache.roleCache1.put(role.getName(),role);
    }
}
