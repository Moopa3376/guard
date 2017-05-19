package net.moopa.guard.model.permission;

import java.util.Date;

/**
 * Created by Moopa on 18/04/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class Permission {
    public int permission_id;
    public String description;
    public String permission_name;           //用来标识这个权限
    Date created_time;
    Date update_time;

    public Permission(int pid,String name,String desc){
        this.permission_id = pid;
        this.permission_name = name;
        this.description = desc;
    }

    public Permission(String name){
        this.permission_name = name;
    }

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
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
}
