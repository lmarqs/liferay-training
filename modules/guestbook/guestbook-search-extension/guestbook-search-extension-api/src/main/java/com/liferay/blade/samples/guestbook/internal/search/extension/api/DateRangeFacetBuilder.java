package com.liferay.blade.samples.guestbook.internal.search.extension.api;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.filter.FilterBuilders;

//@ProviderType
public interface DateRangeFacetBuilder {

	public abstract DateRangeFacet build();

	public abstract DateRangeFacetBuilder setFieldName(String fieldName);

	public abstract DateRangeFacetBuilder setFilterBuilders(
		FilterBuilders filterBuilders);

	public abstract DateRangeFacetBuilder setFrom(String from);

	public abstract DateRangeFacetBuilder setSearchContext(
		SearchContext searchContext);

	public abstract DateRangeFacetBuilder setTo(String to);

}