package br.com.objective.training.web.portlet.util.taglib;

import br.com.objective.training.model.Entry;
import br.com.objective.training.service.permission.EntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntryPermissionTag extends BodyTagSupport {

    private PermissionChecker _permissionChecker;
    private Long _entryId;
    private String _actionId;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (EntryPermission.contains(_permissionChecker, _entryId, _actionId)) {
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

    public void setEntryId(Long entryId) {
        _entryId = entryId;
    }

    public void setActionId(String actionId) {
        _actionId = actionId;
    }
}
