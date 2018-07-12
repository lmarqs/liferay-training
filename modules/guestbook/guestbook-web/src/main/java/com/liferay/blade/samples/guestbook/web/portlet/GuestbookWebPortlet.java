package com.liferay.blade.samples.guestbook.web.portlet;

import com.liferay.blade.samples.guestbook.internal.search.extension.api.FacetBuilders;
import com.liferay.blade.samples.guestbook.model.Entry;
import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.EntryLocalServiceUtil;
import com.liferay.blade.samples.guestbook.service.EntryService;
import com.liferay.blade.samples.guestbook.service.GuestbookLocalServiceUtil;
import com.liferay.blade.samples.guestbook.service.GuestbookService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.filter.FilterBuilders;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.liferay.blade.samples.guestbook.constants.GuestbookWebPortletKeys.*;

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
                "javax.portlet.name=" + GUESTBOOK_WEB_PORTLET,
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
                _renderView(request, response, scopeGroupId, guestbookId);
            }
        } catch (Exception e) {
            Logger.getLogger(GuestbookWebPortlet.class.getName())
                    .log(Level.SEVERE, null, e);
            throw new PortletException(e);
        }

        super.render(request, response);
    }

    private void _renderEdit(RenderRequest request) throws PortalException {
        long entryId = ParamUtil.getLong(request, "entryId");
        request.setAttribute("entry", entryId > 0 ? EntryLocalServiceUtil.getEntry(entryId) : null);
    }

    private void _renderView(RenderRequest request, RenderResponse response, long scopeGroupId, long guestbookId) {
        final List<Guestbook> guestbooks = GuestbookLocalServiceUtil.getGuestbooks(scopeGroupId, WorkflowConstants.STATUS_APPROVED);
        List<NavigationItem> navigationItems = new NavigationItemList() {
            {
                for (Guestbook guestbook : guestbooks) {
                    add(navigationItem -> {
                        if (guestbook.getGuestbookId() == guestbookId) {
                            navigationItem.setActive(true);
                        }


                        PortletURL portletURL = response.createRenderURL();

                        portletURL.setParameter("mvcPath", MVC_PATH_VIEW);
                        portletURL.setParameter("guestbookId", Long.toString(guestbook.getGuestbookId()));

                        navigationItem.setHref(portletURL);
                        navigationItem.setLabel(guestbook.getName());
                    });
                }

            }
        };

        request.setAttribute("navigationItems", navigationItems);

        request.setAttribute("total", EntryLocalServiceUtil.getEntriesCount(scopeGroupId, guestbookId, WorkflowConstants.STATUS_APPROVED));
        request.setAttribute("results",
                EntryLocalServiceUtil.getEntries(
                        scopeGroupId, guestbookId,
                        ParamUtil.getInteger(request, "start", 0),
                        ParamUtil.getInteger(request, "end", 20)
                )
        );

    }

    private void _renderSearch(RenderRequest request, long scopeGroupId) throws SearchException, JSONException {
        String keywords = ParamUtil.getString(request, "keywords");

        SearchContext searchContext;
        searchContext = SearchContextFactory.getInstance(getHttpServletRequest(request));

        searchContext.setKeywords(keywords);
        searchContext.setAttribute("paginationType", "more");
        searchContext.setStart(0);
        searchContext.setEnd(10);

        searchContext.addFacet(_facetBuilders
                .dateRangeFacetBuilder()
                .setFilterBuilders(_filterBuilders)
                .setFieldName(ParamUtil.getString(request, "fieldName"))
                .setSearchContext(searchContext)
                .setFrom(ParamUtil.getString(request, "from"))
                .setTo(ParamUtil.getString(request, "to"))
                .build());

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

        Map<Long, String> guestbookMap = new HashMap<>();

        for (Guestbook guestbook : guestbooks) {
            guestbookMap.put(guestbook.getGuestbookId(), guestbook.getName());
        }


        request.setAttribute("entries", entries);
        request.setAttribute("guestbooks", guestbooks);
        request.setAttribute("guestbookMap", guestbookMap);
    }

    private static HttpServletRequest getHttpServletRequest(final PortletRequest request) {
        return PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
    }

    private long _getAttributeGuestbookId(RenderRequest request, RenderResponse response, ServiceContext serviceContext) throws PortalException {
        long guestbookId = ParamUtil.getLong(request, "guestbookId");

        List<Guestbook> guestbooks = _guestbookService.getGuestbooks(serviceContext.getScopeGroupId());

        if (guestbooks.isEmpty()) {
            Guestbook guestbook = _guestbookService.addGuestbook(serviceContext.getUserId(), "Main", "", 0, new Date(), serviceContext);
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

            _entryService.updateEntry(serviceContext.getUserId(), guestbookId, entryId, userName, email, message, serviceContext);

            response.setRenderParameter("guestbookId", Long.toString(guestbookId));
            SessionMessages.add(request, "entryUpdated");

        } catch (Exception e) {
            Logger.getLogger(GuestbookWebPortlet.class.getName())
                    .log(Level.SEVERE, null, e);
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

            _entryService.addEntry(serviceContext.getUserId(), guestbookId, userName, email, message, serviceContext);

            response.setRenderParameter("guestbookId", Long.toString(guestbookId));
            SessionMessages.add(request, "entryAdded");

        } catch (Exception e) {
            Logger.getLogger(GuestbookWebPortlet.class.getName())
                    .log(Level.SEVERE, null, e);
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

            _entryService.deleteEntry(entryId, serviceContext);

            SessionMessages.add(request, "entryDeleted");
        } catch (Exception e) {
            Logger.getLogger(GuestbookWebPortlet.class.getName())
                    .log(Level.SEVERE, null, e);
            SessionErrors.add(request, e.getClass().getName());
        }
    }

    @Reference(unbind = "-")
    protected void setEntryService(EntryService entryService) {
        _entryService = entryService;
    }

    @Reference(unbind = "-")
    protected void setGuestbookService(GuestbookService guestbookService) {
        _guestbookService = guestbookService;
    }

    @Reference(unbind = "-")
    protected void setFacetBuilders(FacetBuilders facetBuilders) {
        _facetBuilders = facetBuilders;
    }

    @Reference(unbind = "-")
    protected void setFilterBuilders(FilterBuilders filterBuilders) {
        _filterBuilders = filterBuilders;
    }

    private EntryService _entryService;
    private GuestbookService _guestbookService;
    private FacetBuilders _facetBuilders;
    private FilterBuilders _filterBuilders;


}