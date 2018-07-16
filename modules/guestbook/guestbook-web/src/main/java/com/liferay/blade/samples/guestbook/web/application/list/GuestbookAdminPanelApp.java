package com.liferay.blade.samples.guestbook.web.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.blade.samples.guestbook.constants.GuestbookAdminPanelCategoryKeys;
import com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys;
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
		return GuestbookAdminPortletKeys.GUESTBOOK_ADMIN_PORTLET;
	}

	@Override
	@Reference(
			target = "(javax.portlet.name=" + GuestbookAdminPortletKeys.GUESTBOOK_ADMIN_PORTLET + ")",
			unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}