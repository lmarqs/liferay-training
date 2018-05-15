package br.com.objective.training.web.portlet.util.taglib;

import br.com.objective.training.service.permission.GuestbookPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuestbookPermissionTag extends BodyTagSupport {

    private PermissionChecker _permissionChecker;
    private Long _guestbookId;
    private String _actionId;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (GuestbookPermission.contains(_permissionChecker, _guestbookId, _actionId)) {
                return EVAL_BODY_INCLUDE;
            }
        } catch (Exception ignored) {
            Logger.getLogger(EntryPermissionTag.class.getName())
                    .log(Level.SEVERE, null, ignored);
        }
        return SKIP_BODY;
    }

    public void setPermissionChecker(PermissionChecker permissionChecker) {
        _permissionChecker = permissionChecker;
    }

    public void setGuestbookId(Long guestbookId) {
        _guestbookId = guestbookId;
    }

    public void setActionId(String actionId) {
        _actionId = actionId;
    }
}
