package net.moopa.guard.demo;

import net.moopa.guard.model.account.Account;
import net.moopa.guard.model.permission.Permission;
import net.moopa.guard.model.role.Role;
import net.moopa.guard.service.IGuardService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moopa on 18/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class GuardServiceImpl implements IGuardService{

    //这个方法主要用于:在http传输过来的密码基础上做一些操作和数据库中存储的密码相比

    public boolean matchPassword(String password, String anotherPassword) {
        return password.equals(anotherPassword);
    }

    public List<Role> leadIntoRole() {
        List<Role>roleList = new ArrayList<Role>();

        try {
            ResultSet resultSet = DatabaseTool.query("select * from role");
            while(resultSet.next()){
                roleList.add(new Role(Integer.parseInt(resultSet.getString(1)),resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleList;
    }

    public List<Permission> leadIntoPermission() {
        List<Permission> permissionList = new ArrayList<Permission>();
        try {
            ResultSet resultSet = DatabaseTool.query("select * from permission");
            while(resultSet.next()){
                permissionList.add(new Permission(Integer.parseInt(resultSet.getString(1)),resultSet.getString(2),resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissionList;
    }

    public void matchRoleAndPermission(List<Role> roles, List<Permission> permissions) {
        //获得关系
        for(Role role : roles){
            try {
                ResultSet resultSet = DatabaseTool.query("select * from role_permission where role_id="+role.getRole_id());
                while(resultSet.next()){
                    int pid = Integer.parseInt(resultSet.getString(2));
                    for(Permission p : permissions){
                        if(p.permission_id == pid){
                            role.putPermission(p.permission_name,p);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String convertRequestToPermission(ServletRequest servletRequest) {
        String method = ((HttpServletRequest)servletRequest).getMethod();
        String requestPath = ((HttpServletRequest)servletRequest).getPathInfo();
        return requestPath+"|"+method;
    }

    public Account getAccountByLoginname(String loginname) {
        Account account = null;
        try {
            ResultSet resultSet = DatabaseTool.query("select * from account_basicinfo where loginname = "+loginname);
            resultSet.next();
            account = new Account(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void endInit() {
        DatabaseTool.close();
    }
}
