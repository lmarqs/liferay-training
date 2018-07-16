package com.liferay.portal.search.web.internal.modified.facet.portlet.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;

import static com.liferay.blade.samples.guestbook.constants.GuestbookSearchPortletKeys.GUESTBOOK_SEARCH_PORTLET;
import static com.liferay.blade.samples.guestbook.constants.GuestbookSearchPortletKeys.MVC_PATH_CONFIGURATION;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + GUESTBOOK_SEARCH_PORTLET,
        service = ConfigurationAction.class
)
public class GuestbookSearchPortletConfigurationAction extends DefaultConfigurationAction {

    @Override
    public String getJspPath(HttpServletRequest request) {
        return MVC_PATH_CONFIGURATION;
    }

}