package br.com.objective.training.web.portlet;

import br.com.objective.training.model.Entry;
import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.EntryLocalService;
import br.com.objective.training.service.EntryLocalServiceUtil;
import br.com.objective.training.service.GuestbookLocalService;
import br.com.objective.training.service.GuestbookLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static br.com.objective.training.web.constants.GuestbookWebPortletKeys.GUESTBOOK;
import static br.com.objective.training.web.constants.GuestbookWebPortletKeys.MVC_PATH_EDIT;
import static br.com.objective.training.web.constants.GuestbookWebPortletKeys.MVC_PATH_SEARCH;
import static br.com.objective.training.web.constants.GuestbookWebPortletKeys.MVC_PATH_VIEW;

/**
 * @author lucas
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.social",
                "com.liferay.portlet.instanceable=false",
                "com.liferay.portlet.scopeable=true",
                "javax.portlet.display-name=Guestbook",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=" + MVC_PATH_VIEW,
                "javax.portlet.name=" + GUESTBOOK,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.supports.mime-type=text/html"
        },
        service = Portlet.class
)
public class GuestbookWebPortlet extends MVCPortlet {
    @Override
    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        try {

            ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), request);

            long scopeGroupId = serviceContext.getScopeGroupId();
            long guestbookId = _getAttributeGuestbookId(request, response, serviceContext);

            request.setAttribute("guestbookId", guestbookId);

            String mvcPath = ParamUtil.getString(request, "mvcPath");

            if (MVC_PATH_EDIT.equals(mvcPath)) {
                _renderEdit(request);
            } else if (MVC_PATH_SEARCH.equals(mvcPath)) {
                _renderSearch(request, scopeGroupId);
            } else {
                _renderView(request, scopeGroupId, guestbookId);
            }
        } catch (Exception e) {
            throw new PortletException(e);
        }

        super.render(request, response);
    }

    private void _renderEdit(RenderRequest request) throws PortalException {
        long entryId = ParamUtil.getLong(request, "entryId");
        request.setAttribute("entry", entryId > 0 ? EntryLocalServiceUtil.getEntry(entryId) : null);
    }

    private void _renderView(RenderRequest request, long scopeGroupId, long guestbookId) {
        request.setAttribute("guestbooks", GuestbookLocalServiceUtil.getGuestbooks(scopeGroupId));

        request.setAttribute("total", EntryLocalServiceUtil.getEntriesCount(scopeGroupId, guestbookId));
        request.setAttribute("results",
                EntryLocalServiceUtil.getEntries(
                        scopeGroupId,
                        guestbookId,
                        ParamUtil.getInteger(request, "start", 0),
                        ParamUtil.getInteger(request, "end", 20)
                )
        );

    }

    private void _renderSearch(RenderRequest request, long scopeGroupId) throws SearchException {
        String keywords = ParamUtil.getString(request, "keywords");

        SearchContext searchContext;
        searchContext = SearchContextFactory.getInstance(getHttpServletRequest(request));

        searchContext.setKeywords(keywords);
        searchContext.setAttribute("paginationType", "more");
        searchContext.setStart(0);
        searchContext.setEnd(10);

        Indexer indexer = IndexerRegistryUtil.getIndexer(Entry.class);

        Hits hits = indexer.search(searchContext);

        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < hits.getDocs().length; i++) {
            Document doc = hits.doc(i);

            long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

            try {
                Entry entry = EntryLocalServiceUtil.getEntry(entryId);
                entries.add(entry);
            } catch (PortalException | SystemException e) {
                Logger.getLogger(GuestbookWebPortlet.class.getName())
                        .log(Level.WARNING, null, e);
            }
        }

        List<Guestbook> guestbooks = GuestbookLocalServiceUtil.getGuestbooks(scopeGroupId);

        Map<String, String> guestbookMap = new HashMap<>();

        for (Guestbook guestbook : guestbooks) {
            guestbookMap.put(Long.toString(guestbook.getGuestbookId()), guestbook.getName());
        }


        request.setAttribute("entries", entries);
        request.setAttribute("guestbookMap", guestbookMap);
    }

    private static HttpServletRequest getHttpServletRequest(final PortletRequest request) {
        return PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
    }

    private long _getAttributeGuestbookId(RenderRequest request, RenderResponse response, ServiceContext serviceContext) throws PortalException {
        long guestbookId = ParamUtil.getLong(request, "guestbookId");

        List<Guestbook> guestbooks = _guestbookLocalService.getGuestbooks(serviceContext.getScopeGroupId());

        if (guestbooks.isEmpty()) {
            Guestbook guestbook = _guestbookLocalService.addGuestbook(serviceContext.getUserId(), "Main", serviceContext);
            guestbookId = guestbook.getGuestbookId();
        }

        if (guestbookId == 0) {
            guestbookId = guestbooks.get(0).getGuestbookId();
        }

        return guestbookId;
    }

    public void updateEntry(ActionRequest request, ActionResponse response) throws PortalException {
        try {
            ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);

            long entryId = ParamUtil.getLong(request, "entryId");
            long guestbookId = ParamUtil.getLong(request, "guestbookId");

            String userName = ParamUtil.getString(request, "name");
            String email = ParamUtil.getString(request, "email");
            String message = ParamUtil.getString(request, "message");

            _entryLocalService.updateEntry(serviceContext.getUserId(), guestbookId, entryId, userName, email, message, serviceContext);

            response.setRenderParameter("guestbookId", Long.toString(guestbookId));
            SessionMessages.add(request, "entryUpdated");

        } catch (Exception e) {
            SessionErrors.add(request, e.getClass().getName());
            PortalUtil.copyRequestParameters(request, response);
            response.setRenderParameter("mvcPath", MVC_PATH_EDIT);
        }
    }


    public void addEntry(ActionRequest request, ActionResponse response) throws PortalException {
        try {
            ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);

            long guestbookId = ParamUtil.getLong(request, "guestbookId");

            String userName = ParamUtil.getString(request, "name");
            String email = ParamUtil.getString(request, "email");
            String message = ParamUtil.getString(request, "message");

            _entryLocalService.addEntry(serviceContext.getUserId(), guestbookId, userName, email, message, serviceContext);

            response.setRenderParameter("guestbookId", Long.toString(guestbookId));
            SessionMessages.add(request, "entryAdded");

        } catch (Exception e) {
            SessionErrors.add(request, e.getClass().getName());
            PortalUtil.copyRequestParameters(request, response);
            response.setRenderParameter("mvcPath", MVC_PATH_EDIT);
        }
    }


    public void deleteEntry(ActionRequest request, ActionResponse response) throws PortalException {
        try {
            ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);

            long entryId = ParamUtil.getLong(request, "entryId");
            long guestbookId = ParamUtil.getLong(request, "guestbookId");

            response.setRenderParameter("guestbookId", Long.toString(guestbookId));

            _entryLocalService.deleteEntry(entryId, serviceContext);

            SessionMessages.add(request, "entryDeleted");
        } catch (Exception e) {
            Logger.getLogger(GuestbookWebPortlet.class.getName())
                    .log(Level.SEVERE, null, e);
            SessionErrors.add(request, e.getClass().getName());
        }
    }

    @Reference(unbind = "-")
    protected void setEntryService(EntryLocalService entryLocalService) {
        _entryLocalService = entryLocalService;
    }

    @Reference(unbind = "-")
    protected void setGuestbookService(GuestbookLocalService guestbookLocalService) {
        _guestbookLocalService = guestbookLocalService;
    }

    private EntryLocalService _entryLocalService;
    private GuestbookLocalService _guestbookLocalService;
}