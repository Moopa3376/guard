package net.moopa.guard.checker;

import net.moopa.guard.model.permission.Permission;
import net.moopa.guard.model.role.Role;

/**
 * Created by Moopa on 19/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class PermissionChecker {
    public  boolean permissionCheck(Role role, String permissionName){
        return role.getAllowedActionList().containsKey(permissionName) ? true : false;
    }
}
