package br.com.objective.training.service.asset;

import br.com.objective.training.model.Entry;
import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.GuestbookLocalServiceUtil;
import br.com.objective.training.service.permission.EntryPermission;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static br.com.objective.training.constants.GuestbookWebPortletKeys.GUESTBOOK_WEB_PORTLET;
import static br.com.objective.training.constants.GuestbookWebPortletKeys.MVC_PATH_EDIT;
import static br.com.objective.training.constants.GuestbookWebPortletKeys.MVC_PATH_VIEW;
import static javax.portlet.PortletRequest.RENDER_PHASE;

public class EntryAssetRenderer extends BaseJSPAssetRenderer<Entry> {

    public EntryAssetRenderer(Entry entry) {
        _entry = entry;
    }

    @Override
    public boolean hasViewPermission(PermissionChecker permissionChecker) throws PortalException {
        long entryId = _entry.getEntryId();
        return EntryPermission.contains(permissionChecker, entryId, ActionKeys.VIEW);
    }

    @Override
    public Entry getAssetObject() {
        return _entry;
    }

    @Override
    public long getGroupId() {
        return _entry.getGroupId();
    }

    @Override
    public long getUserId() {
        return _entry.getUserId();
    }

    @Override
    public String getUserName() {
        return _entry.getUserName();
    }

    @Override
    public String getUuid() {
        return _entry.getUuid();
    }

    @Override
    public String getClassName() {
        return Entry.class.getName();
    }

    @Override
    public long getClassPK() {
        return _entry.getEntryId();
    }

    @Override
    public String getSummary(PortletRequest request, PortletResponse response) {
        return "Name: " + _entry.getName() + ". Message: " + _entry.getMessage();
    }

    @Override
    public String getTitle(Locale locale) {
        return _entry.getMessage();
    }

    @Override
    public boolean include(HttpServletRequest request, HttpServletResponse response, String template) throws Exception {
        request.setAttribute("entry", _entry);
        request.setAttribute("HtmlUtil", HtmlUtil.getHtml());
        request.setAttribute("StringUtil", new StringUtil());
        return super.include(request, response, template);
    }

    @Override
    public String getJspPath(HttpServletRequest request, String template) {
        if (template.equals(TEMPLATE_FULL_CONTENT)) {

            try {
                Guestbook guestbook = GuestbookLocalServiceUtil.getGuestbook(_entry.getGuestbookId());
                request.setAttribute("guestbook", guestbook);
            } catch (PortalException e) {
                Logger.getLogger(GuestbookAssetRenderer.class.getName())
                        .log(Level.SEVERE, null, e);
            }
            request.setAttribute("entry", _entry);

            return "/asset/entry/" + template + ".jsp";
        }

        return null;
    }

    @Override
    public PortletURL getURLEdit(LiferayPortletRequest request, LiferayPortletResponse response) throws Exception {
        PortletURL portletURL = response.createLiferayPortletURL(getControlPanelPlid(request), GUESTBOOK_WEB_PORTLET, RENDER_PHASE);
        portletURL.setParameter("mvcRenderCommandName", MVC_PATH_EDIT);
        portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));
        portletURL.setParameter("showback", Boolean.FALSE.toString());

        return portletURL;
    }

    @Override
    public String getURLViewInContext(LiferayPortletRequest request, LiferayPortletResponse response, String noSuchEntryRedirect) throws Exception {

        try {

            long plid = PortalUtil.getPlidFromPortletId(_entry.getGroupId(), GUESTBOOK_WEB_PORTLET);

            PortletURL portletURL;
            if (plid == LayoutConstants.DEFAULT_PLID) {
                portletURL = response.createLiferayPortletURL(getControlPanelPlid(request), GUESTBOOK_WEB_PORTLET, RENDER_PHASE);
            } else {
                portletURL = PortletURLFactoryUtil.create(request, GUESTBOOK_WEB_PORTLET, plid, RENDER_PHASE);
            }

            portletURL.setParameter("mvcRenderCommandName", MVC_PATH_VIEW);
            portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));

            String currentUrl = PortalUtil.getCurrentURL(request);

            portletURL.setParameter("redirect", currentUrl);

            return portletURL.toString();

        } catch (PortalException | SystemException e) {
            Logger.getLogger(GuestbookAssetRenderer.class.getName())
                    .log(Level.SEVERE, null, e);
        }

        return noSuchEntryRedirect;
    }

    @Override
    public String getURLView(LiferayPortletResponse response, WindowState windowState) throws Exception {
        return super.getURLView(response, windowState);
    }

    @Override
    public boolean isPrintable() {
        return true;
    }

    private Entry _entry;
}