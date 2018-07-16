package com.liferay.blade.samples.guestbook.web.portlet;

import static com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys.*;

import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.GuestbookLocalServiceUtil;
import com.liferay.blade.samples.guestbook.service.GuestbookService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author lucas
 */
@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.add-default-resource=true",
				"com.liferay.portlet.display-category=category.hidden",
				"com.liferay.portlet.layout-cacheable=true",
				"com.liferay.portlet.private-request-attributes=false",
				"com.liferay.portlet.private-session-attributes=false",
				"com.liferay.portlet.render-weight=50",
				"com.liferay.portlet.scopeable=true",
				"com.liferay.portlet.use-default-template=true",

				"javax.portlet.display-name=Guestbooks",
				"javax.portlet.expiration-cache=0",
				"javax.portlet.init-param.portlet-title-based-navigation=true",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=" + MVC_PATH_VIEW,
				"javax.portlet.name=" + GUESTBOOK_ADMIN_PORTLET,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=administrator",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class GuestbookAdminPortlet extends MVCPortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
	throws IOException, PortletException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
	Guestbook.class.getName(), request);

			String mvcPath = ParamUtil.getString(request, "mvcPath");

			if (MVC_PATH_EDIT.equals(mvcPath)) {
				long guestbookId = ParamUtil.getLong(request, "guestbookId");

				if (guestbookId > 0) {
					Guestbook guestbook =
	GuestbookLocalServiceUtil.getGuestbook(guestbookId);
					Calendar eventDate = Calendar.getInstance();

					eventDate.setTime(guestbook.getEventDate());

					request.setAttribute("eventDate", eventDate);
					request.setAttribute("guestbook", guestbook);
				}

			} else {
				long scopeGroupId = serviceContext.getScopeGroupId();
				request.setAttribute("total", GuestbookLocalServiceUtil
								.getGuestbooksCount(scopeGroupId));

				request.setAttribute("results",
						GuestbookLocalServiceUtil.getGuestbooks(
								scopeGroupId,
								ParamUtil.getInteger(request, "start", 0),
								ParamUtil.getInteger(request, "end", 20)));
			}
		} catch (Exception e) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName())
				.log(Level.SEVERE, null, e);

			throw new PortletException(e);
		}

		super.render(request, response);
	}

	public void addGuestbook(ActionRequest request, ActionResponse response)
	throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
	Guestbook.class.getName(), request);

		String name = ParamUtil.getString(request, "name");
		String note = ParamUtil.getString(request, "note");
		Integer priority = ParamUtil.getInteger(request, "priority");
		Date eventDate = ParamUtil.getDate(
	request, "eventDate", new SimpleDateFormat("MM/dd/yyyy"));

		try {
			_guestbookService.addGuestbook(
	serviceContext.getUserId(), name, note, priority, eventDate,
	serviceContext); SessionMessages.add(request, "guestbookAdded");
		} catch (PortalException pe) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName())
				.log(Level.SEVERE, null, pe);

			response.setRenderParameter("mvcPath", MVC_PATH_EDIT);
		}
	}

	public void updateGuestbook(ActionRequest request, ActionResponse response)
	throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
	Guestbook.class.getName(), request);

		long guestbookId = ParamUtil.getLong(request, "guestbookId");

		String name = ParamUtil.getString(request, "name");
		String note = ParamUtil.getString(request, "note");
		Integer priority = ParamUtil.getInteger(request, "priority");
		Date eventDate = ParamUtil.getDate(
	request, "eventDate", new SimpleDateFormat("MM/dd/yyyy"));

		try {
			_guestbookService.updateGuestbook(
	serviceContext.getUserId(), guestbookId, name, note, priority, eventDate,
	serviceContext); SessionMessages.add(request, "guestbookUpdated");
		} catch (PortalException pe) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName())
				.log(Level.SEVERE, null, pe);
			SessionErrors.add(request, pe.getClass().getName());
			response.setRenderParameter("mvcPath", MVC_PATH_EDIT);
		}
	}

	public void deleteGuestbook(ActionRequest request, ActionResponse response)
	throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
	Guestbook.class.getName(), request);

		long guestbookId = ParamUtil.getLong(request, "guestbookId");

		try {
			_guestbookService.deleteGuestbook(guestbookId, serviceContext);
			SessionMessages.add(request, "guestbookDeleted");
		} catch (PortalException pe) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName())
				.log(Level.SEVERE, null, pe);
			SessionErrors.add(request, pe.getClass().getName());
		}
	}

	@Reference(unbind = "-")
	protected void setGuestbookService(GuestbookService guestbookService) {
		_guestbookService = guestbookService;
	}

	private GuestbookService _guestbookService;

}