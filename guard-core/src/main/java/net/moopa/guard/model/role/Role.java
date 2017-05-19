package net.moopa.guard.model.role;


import net.moopa.guard.model.permission.Permission;

import java.util.Date;
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
    String description = null;
    Date created_time;
    Date update_time;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public void setAllowedActionList(Map<String, Permission> allowedActionList) {
        this.allowedActionList = allowedActionList;
    }
}
