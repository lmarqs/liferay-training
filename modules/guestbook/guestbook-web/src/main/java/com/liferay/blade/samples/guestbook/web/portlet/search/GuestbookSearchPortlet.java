package com.liferay.blade.samples.guestbook.web.portlet.search;

import static com.liferay.blade.samples.guestbook.constants.GuestbookSearchPortletKeys.GUESTBOOK_SEARCH_PORTLET;
import static com.liferay.blade.samples.guestbook.constants.GuestbookSearchPortletKeys.MVC_PATH_VIEW;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.display-category=category.social",
				"com.liferay.portlet.header-portlet-css=/lib/css/main.css",
				"com.liferay.portlet.instanceable=false",
				"com.liferay.portlet.scopeable=true",
				"javax.portlet.display-name=Guestbook Search",
				"javax.portlet.expiration-cache=0",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=" + MVC_PATH_VIEW,
				"javax.portlet.name=" + GUESTBOOK_SEARCH_PORTLET,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=power-user,user",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class GuestbookSearchPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
	portletSharedSearchRequest.search(renderRequest);

		renderRequest.setAttribute(
	"totalHits", portletSharedSearchResponse.getTotalHits());

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}