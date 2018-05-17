package br.com.objective.training.asset;

import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.GuestbookLocalService;
import br.com.objective.training.service.permission.GuestbookPermission;
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

import static br.com.objective.training.constants.GuestbookAdminPortletKeys.GUESTBOOK_ADMIN;
import static com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY;
import static javax.portlet.PortletRequest.RENDER_PHASE;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + GUESTBOOK_ADMIN
        },
        service = AssetRendererFactory.class
)
public class GuestbookAssetRendererFactory extends BaseAssetRendererFactory<Guestbook> {

    public GuestbookAssetRendererFactory() {
        setClassName(CLASS_NAME);
        setLinkable(_LINKABLE);
        setPortletId(GUESTBOOK_ADMIN);
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

            portletURL = response.createLiferayPortletURL(getControlPanelPlid(themeDisplay), GUESTBOOK_ADMIN, RENDER_PHASE);
            portletURL.setParameter("mvcRenderCommandName", GUESTBOOK_ADMIN);
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

    @Reference(target = "(osgi.web.symbolicname=br.com.objective.training.web.portlet)", unbind = "-")
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