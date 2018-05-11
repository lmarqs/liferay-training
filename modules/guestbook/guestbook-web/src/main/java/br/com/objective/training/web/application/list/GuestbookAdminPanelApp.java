package br.com.objective.training.web.application.list;

import br.com.objective.training.web.constants.GuestbookAdminPanelCategoryKeys;
import br.com.objective.training.web.constants.GuestbookAdminPortletKeys;
import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author lucas
 */
@Component(
        immediate = true,
        property = {
                "panel.app.order:Integer=100",
                "panel.category.key=" + GuestbookAdminPanelCategoryKeys.CONTROL_PANEL_CATEGORY
        },
        service = PanelApp.class
)
public class GuestbookAdminPanelApp extends BasePanelApp {

    @Override
    public String getPortletId() {
        return GuestbookAdminPortletKeys.GUESTBOOK_ADMIN;
    }

    @Override
    @Reference(
            target = "(javax.portlet.name=" + GuestbookAdminPortletKeys.GUESTBOOK_ADMIN + ")",
            unbind = "-"
    )
    public void setPortlet(Portlet portlet) {
        super.setPortlet(portlet);
    }

}