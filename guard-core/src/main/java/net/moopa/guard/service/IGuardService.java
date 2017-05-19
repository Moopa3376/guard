package net.moopa.guard.service;

import net.moopa.guard.model.account.Account;
import net.moopa.guard.model.permission.Permission;
import net.moopa.guard.model.role.Role;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * Created by Moopa on 16/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public interface IGuardService {
    public boolean matchPassword(String password, String anotherPassword);

    //以下是导入role,permission,由于permission中有相应的role_id,guard自动去整合相应关系.
    public List<Role> leadIntoRole();
    public List<Permission> leadIntoPermission();
    public void matchRoleAndPermission(List<Role> roles,List<Permission> permissions);

    //将http请求妆花成permission
    public String convertRequestToPermission(ServletRequest servletRequest);


    //以下是获取account的接口
    public Account getAccountByLoginname(String loginname);

    //结束初始化过程
    public void endInit();
}
