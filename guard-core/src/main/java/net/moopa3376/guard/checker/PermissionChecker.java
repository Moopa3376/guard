package net.moopa3376.guard.checker;

import net.moopa3376.guard.model.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Moopa on 19/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class PermissionChecker {
    private static Logger logger = LoggerFactory.getLogger(PermissionChecker.class);

    public  boolean permissionCheck(Role role, String permissionName){
        return role.getAllowedActionMap().containsKey(permissionName) ? true : false;
    }
}
