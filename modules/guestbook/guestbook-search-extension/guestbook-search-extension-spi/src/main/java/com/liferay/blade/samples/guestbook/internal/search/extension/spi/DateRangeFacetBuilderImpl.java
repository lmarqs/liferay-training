package com.liferay.blade.samples.guestbook.internal.search.extension.spi;


import com.liferay.blade.samples.guestbook.internal.search.extension.api.DateRangeFacet;
import com.liferay.blade.samples.guestbook.internal.search.extension.api.DateRangeFacetBuilder;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.filter.FilterBuilders;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        service = DateRangeFacetBuilder.class
)
public class DateRangeFacetBuilderImpl implements DateRangeFacetBuilder {

    @Override
    public DateRangeFacet build() {
        DateRangeFacet dateRangeFacet = new DateRangeFacetImpl(_fieldName, _searchContext, _filterBuilders);

        dateRangeFacet.setFrom(_from);
        dateRangeFacet.setTo(_to);

        return dateRangeFacet;
    }

    @Override
    public DateRangeFacetBuilder setFieldName(String fieldName) {
        _fieldName = fieldName;

        return this;
    }

    @Override
    public DateRangeFacetBuilder setFrom(String from) {
        _from = from;

        return this;
    }

    @Override
    public DateRangeFacetBuilder setSearchContext(SearchContext searchContext) {
        _searchContext = searchContext;

        return this;
    }

    @Override
    public DateRangeFacetBuilder setTo(String to) {
        _to = to;

        return this;
    }

    @Override
    public DateRangeFacetBuilder setFilterBuilders(FilterBuilders filterBuilders) {
        _filterBuilders = filterBuilders;
        return this;
    }

    private FilterBuilders _filterBuilders;

    private SearchContext _searchContext;
    private String _fieldName;
    private String _from;
    private String _to;
}
