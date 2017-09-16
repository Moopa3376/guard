package net.moopa3376.guard.service;

import net.moopa3376.guard.api.ApiCheckResult;
import net.moopa3376.guard.http.HttpRequestMethod;
import net.moopa3376.guard.model.account.Account;
import net.moopa3376.guard.model.permission.Permission;
import net.moopa3376.guard.model.role.Role;
import org.slf4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.util.List;

/**
 * Created by Moopa on 16/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public interface IGuardService {
    public boolean matchPassword(String password, String anotherPassword);

    /**
     * 以下是导入role,permission,由于permission中有相应的role_id,guard自动去整合相应关系.
     * @return
     */
    public List<Role> leadIntoRole();
    public List<Permission> leadIntoPermission();
    public void matchRoleAndPermission(List<Role> roles,List<Permission> permissions);

    /**
     * 将http请求化成permission
     * @param servletRequest
     * @return
     */
    public String convertRequestToPermission(ServletRequest servletRequest);

    /**
     * 决定请求是否需要被过滤
     * @param permission
     * @return
     */
    public boolean isExcludeUrl(String permission);

    /**
     * 以下是获取account的接口 - 主要是提供给Guard
     * @param name
     * @return
     */
    public Account getAccountByName(String name);


    public String apiNameDefinetion(String path, HttpRequestMethod httpRequestMethod);

    public void errorHandle(ServletResponse servletResponse, String path, ApiCheckResult apiCheckResult, Logger logger);

    /**
     * 结束初始化过程
     */
    public void endInit();
}
