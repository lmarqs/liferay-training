package com.liferay.blade.samples.guestbook.web.portlet.util.taglib;

import com.liferay.blade.samples.guestbook.service.permission.GuestbookPermission;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class GuestbookPermissionTag extends BodyTagSupport {

	@Override
	public int doStartTag() throws JspException {
		try {
			if (GuestbookPermission.contains(
_permissionChecker, _guestbookId, _actionId)) {

				return EVAL_BODY_INCLUDE;
			}
		} catch (Exception ignored) {
			Logger.getLogger(EntryPermissionTag.class.getName())
				.log(Level.SEVERE, null, ignored);
		}

		return SKIP_BODY;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public void setGuestbookId(Long guestbookId) {
		_guestbookId = guestbookId;
	}

	public void setPermissionChecker(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	private String _actionId;
	private Long _guestbookId;
	private PermissionChecker _permissionChecker;

}