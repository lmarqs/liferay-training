package com.liferay.blade.samples.guestbook.service.permission;

import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = "model.class.name=com.liferay.blade.samples.guestbook.model.Guestbook"
)
@Deprecated
public class GuestbookPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long guestbookId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
	permissionChecker, Guestbook.class.getName(), guestbookId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long guestbookId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, guestbookId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Guestbook guestbook,
			String actionId)
		throws PortalException {

		return permissionChecker.hasPermission(
	guestbook.getGroupId(), Guestbook.class.getName(),
	guestbook.getGuestbookId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long guestbookId,
			String actionId)
		throws PortalException {

		Guestbook guestbook = _guestbookLocalService.getGuestbook(guestbookId);

		return GuestbookModelPermission.contains(
	permissionChecker, groupId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long guestbookId,
			String actionId)
		throws PortalException {

		Guestbook guestbook = _guestbookLocalService.getGuestbook(guestbookId);

		return contains(permissionChecker, guestbook, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long guestbookId,
			String actionId)
		throws PortalException {

		check(permissionChecker, guestbookId, actionId);
	}

	@Reference(unbind = "-")
	protected void setGuestbookLocalService(
		GuestbookLocalService guestbookLocalService) {

		_guestbookLocalService = guestbookLocalService;
	}

	private static GuestbookLocalService _guestbookLocalService;

}