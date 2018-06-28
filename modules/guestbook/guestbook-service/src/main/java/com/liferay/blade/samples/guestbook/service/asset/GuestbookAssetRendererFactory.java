package com.liferay.blade.samples.guestbook.service.asset;

import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.GuestbookLocalService;
import com.liferay.blade.samples.guestbook.service.permission.GuestbookPermission;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletURL;
import javax.servlet.ServletContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys.GUESTBOOK_ADMIN_PORTLET;
import static com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys.MVC_PATH_EDIT;
import static com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY;
import static javax.portlet.PortletRequest.RENDER_PHASE;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + GUESTBOOK_ADMIN_PORTLET
        },
        service = AssetRendererFactory.class
)
public class GuestbookAssetRendererFactory extends BaseAssetRendererFactory<Guestbook> {

    public GuestbookAssetRendererFactory() {
        setClassName(CLASS_NAME);
        setLinkable(_LINKABLE);
        setPortletId(GUESTBOOK_ADMIN_PORTLET);
        setSearchable(true);
        setSelectable(true);
    }

    @Override
    public AssetRenderer<Guestbook> getAssetRenderer(long classPK, int type) throws PortalException {

        Guestbook guestbook = _guestbookLocalService.getGuestbook(classPK);

        GuestbookAssetRenderer guestbookAssetRenderer = new GuestbookAssetRenderer(guestbook);

        guestbookAssetRenderer.setAssetRendererType(type);
        guestbookAssetRenderer.setServletContext(_servletContext);

        return guestbookAssetRenderer;
    }

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean hasPermission(PermissionChecker permissionChecker, long classPK, String actionId) throws Exception {
        Guestbook guestbook = _guestbookLocalService.getGuestbook(classPK);
        return GuestbookPermission.contains(permissionChecker, guestbook, actionId);
    }

    @Override
    public PortletURL getURLAdd(LiferayPortletRequest request, LiferayPortletResponse response, long classTypeId) {
        PortletURL portletURL = null;

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(THEME_DISPLAY);

            portletURL = response.createLiferayPortletURL(getControlPanelPlid(themeDisplay), GUESTBOOK_ADMIN_PORTLET, RENDER_PHASE);
            portletURL.setParameter("mvcPath", MVC_PATH_EDIT);
            portletURL.setParameter("showback", Boolean.FALSE.toString());
        } catch (PortalException e) {
            Logger.getLogger(GuestbookAssetRendererFactory.class.getName())
                    .log(Level.SEVERE, null, e);
        }

        return portletURL;
    }

    @Override
    public boolean isLinkable() {
        return _LINKABLE;
    }

    @Override
    public String getIconCssClass() {
        return "bookmarks";
    }

    @Reference(target = "(osgi.web.symbolicname=com.liferay.blade.samples.guestbook.web)", unbind = "-")
    public void setServletContext(ServletContext servletContext) {
        _servletContext = servletContext;
    }

    private ServletContext _servletContext;

    @Reference(unbind = "-")
    protected void setGuestbookLocalService(GuestbookLocalService guestbookLocalService) {
        _guestbookLocalService = guestbookLocalService;
    }

    private GuestbookLocalService _guestbookLocalService;
    private static final boolean _LINKABLE = true;
    public static final String CLASS_NAME = Guestbook.class.getName();
    public static final String TYPE = "guestbook";
}