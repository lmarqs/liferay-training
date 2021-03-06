package com.liferay.blade.samples.guestbook.service.asset;

import static com.liferay.blade.samples.guestbook.constants.GuestbookWebPortletKeys.GUESTBOOK_WEB_PORTLET;
import static com.liferay.blade.samples.guestbook.constants.GuestbookWebPortletKeys.MVC_PATH_EDIT;
import static com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY;

import static javax.portlet.PortletRequest.RENDER_PHASE;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.blade.samples.guestbook.model.Entry;
import com.liferay.blade.samples.guestbook.service.EntryLocalService;
import com.liferay.blade.samples.guestbook.service.permission.EntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = "javax.portlet.name=" + GUESTBOOK_WEB_PORTLET,
		service = AssetRendererFactory.class
)
public class EntryAssetRendererFactory extends BaseAssetRendererFactory<Entry> {

	public static final String CLASS_NAME = Entry.class.getName();

	public static final String TYPE = "entry";

	public EntryAssetRendererFactory() {
		setClassName(CLASS_NAME);
		setLinkable(_LINKABLE);
		setPortletId(GUESTBOOK_WEB_PORTLET);
		setSearchable(true);
		setSelectable(true);
	}

	@Override
	public AssetRenderer<Entry> getAssetRenderer(long classPK, int type)
		throws PortalException {

		Entry entry = _entryLocalService.getEntry(classPK);

		EntryAssetRenderer entryAssetRenderer = new EntryAssetRenderer(entry);

		entryAssetRenderer.setAssetRendererType(type);
		entryAssetRenderer.setServletContext(_servletContext);

		return entryAssetRenderer;
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getIconCssClass() {
		return "pencil";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest request, LiferayPortletResponse response,
		long classTypeId) {

		PortletURL portletURL = null;

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
	THEME_DISPLAY);

			portletURL = response.createLiferayPortletURL(
	getControlPanelPlid(themeDisplay), GUESTBOOK_WEB_PORTLET, RENDER_PHASE);

			portletURL.setParameter("mvcRenderCommandName", MVC_PATH_EDIT);
			portletURL.setParameter("showback", Boolean.FALSE.toString());

		} catch (PortalException e) {
			Logger.getLogger(GuestbookAssetRenderer.class.getName())
				.log(Level.SEVERE, null, e);
		}

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse response, WindowState windowState) {

		LiferayPortletURL liferayPortletURL = response.createLiferayPortletURL(
	GUESTBOOK_WEB_PORTLET, RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		} catch (WindowStateException wse) {
			Logger.getLogger(GuestbookAssetRenderer.class.getName())
				.log(Level.SEVERE, null, wse);
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		Entry entry = _entryLocalService.getEntry(classPK);

		return EntryPermission.contains(permissionChecker, entry, actionId);
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	@Reference(target = "(osgi.web.symbolicname=com.liferay.blade.samples.guestbook.web)", unbind = "-")
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setEntryLocalService(EntryLocalService entryLocalService) {
		_entryLocalService = entryLocalService;
	}

	private static final boolean _LINKABLE = true;

	private EntryLocalService _entryLocalService;
	private ServletContext _servletContext;

}