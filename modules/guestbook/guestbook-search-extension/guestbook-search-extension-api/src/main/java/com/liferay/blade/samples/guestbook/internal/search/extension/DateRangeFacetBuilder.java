package com.liferay.blade.samples.guestbook.internal.search.extension;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.search.SearchContext;

@ProviderType
public interface DateRangeFacetBuilder {

    public abstract DateRangeFacet build();

    public abstract DateRangeFacetBuilder setFieldName(String fieldName);

    public abstract DateRangeFacetBuilder setFrom(String from);

    public abstract DateRangeFacetBuilder setSearchContext(SearchContext searchContext);

    public abstract DateRangeFacetBuilder setTo(String to);

}
