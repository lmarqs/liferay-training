package com.liferay.blade.samples.guestbook.web.portlet.search;


import com.liferay.blade.samples.guestbook.internal.search.extension.api.FacetBuilders;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.RenderRequest;

import static com.liferay.blade.samples.guestbook.constants.GuestbookSearchPortletKeys.GUESTBOOK_SEARCH_PORTLET;


@Component(
        immediate = true,
        property = "javax.portlet.name=" + GUESTBOOK_SEARCH_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class GuestbookSearchPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        RenderRequest renderRequest = portletSharedSearchSettings.getRenderRequest();

        String keywords = ParamUtil.getString(renderRequest, "keywords");
        String fieldName = ParamUtil.getString(renderRequest, "fieldName");
        String from = ParamUtil.getString(renderRequest, "from");
        String to = ParamUtil.getString(renderRequest, "to");

        SearchContext searchContext = portletSharedSearchSettings.getSearchContext();

        if (!keywords.isEmpty()) {
            searchContext.setKeywords(keywords);
        }

        if (fieldName.isEmpty() || from.isEmpty() && to.isEmpty()) {
            return;
        }

        if (keywords.isEmpty()) {
            searchContext.setAttribute(SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH, Boolean.TRUE);
        }

        portletSharedSearchSettings.addFacet(
                _facetBuilders
                        .dateRangeFacetBuilder()
                        .setFilterBuilders(_filterBuilders)
                        .setFieldName(fieldName)
                        .setSearchContext(searchContext)
                        .setFrom(from)
                        .setTo(to)
                        .build()
        );


    }


    @Reference(unbind = "-")
    protected void setFacetBuilders(FacetBuilders facetBuilders) {
        _facetBuilders = facetBuilders;
    }

    @Reference(unbind = "-")
    protected void setFilterBuilders(FilterBuilders filterBuilders) {
        _filterBuilders = filterBuilders;
    }

    private FacetBuilders _facetBuilders;
    private FilterBuilders _filterBuilders;

}