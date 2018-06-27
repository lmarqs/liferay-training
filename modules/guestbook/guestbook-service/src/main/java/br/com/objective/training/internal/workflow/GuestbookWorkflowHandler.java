package br.com.objective.training.internal.workflow;

import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

@Component(
        immediate = true,
        service = WorkflowHandler.class
)
public class GuestbookWorkflowHandler extends BaseWorkflowHandler<Guestbook> {
    @Override
    public String getClassName() {
        return Guestbook.class.getName();
    }

    @Override
    public String getType(Locale locale) {
        return _resourceActions.getModelResource(locale, getClassName());
    }

    @Override
    public Guestbook updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {

        long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
        long resourcePrimKey = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

        ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");

        return _guestbookLocalService.updateStatus(userId, resourcePrimKey, status, serviceContext);
    }

    @Reference(unbind = "-")
    protected void setResourceActions(ResourceActions resourceActions) {
        _resourceActions = resourceActions;
    }

    @Reference(unbind = "-")
    protected void setGuestbookLocalService(GuestbookLocalService guestbookLocalService) {
        _guestbookLocalService = guestbookLocalService;
    }

    private GuestbookLocalService _guestbookLocalService;

    private ResourceActions _resourceActions;
}
