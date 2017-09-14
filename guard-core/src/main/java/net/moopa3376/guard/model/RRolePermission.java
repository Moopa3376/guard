package net.moopa3376.guard.model;

/**
 * Created by Moopa on 20/07/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class RRolePermission {
    Long id;
    Long roleId;
    Long permissionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
}
