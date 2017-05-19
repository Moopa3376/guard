package net.moopa.guard.model.role;


import net.moopa.guard.model.permission.Permission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Moopa on 18/04/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class Role {
    int role_id;
    String rolename;
    Map<String,Permission> allowedActionList = new ConcurrentHashMap<String, Permission>();

    public Role(int id, String name){
        this.role_id = id;
        this.rolename = name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Map<String, Permission> getAllowedActionList() {
        return allowedActionList;
    }

    public void putPermission(String permission,Permission p) {
        allowedActionList.put(permission,p);
    }
}
