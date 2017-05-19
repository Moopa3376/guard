package net.moopa.guard.model.permission;

/**
 * Created by Moopa on 18/04/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class Permission {
    public int pid;
    public String permissionName;           //用来标识这个权限
    public String permissionDesc = "null";

    public Permission(int pid,String name,String desc){
        this.pid = pid;
        this.permissionName = name;
        this.permissionDesc = desc;
    }

    public Permission(String name){
        this.permissionName = name;
    }
}
