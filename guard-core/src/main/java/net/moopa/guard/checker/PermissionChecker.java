package net.moopa.guard.checker;

import net.moopa.guard.config.Configs;
import net.moopa.guard.model.permission.Permission;
import net.moopa.guard.model.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * Created by Moopa on 19/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class PermissionChecker {
    private static Logger logger = LoggerFactory.getLogger(PermissionChecker.class);

    public  boolean permissionCheck(Role role, String permissionName){
        return role.getAllowedActionList().containsKey(permissionName) ? true : false;
    }
}
