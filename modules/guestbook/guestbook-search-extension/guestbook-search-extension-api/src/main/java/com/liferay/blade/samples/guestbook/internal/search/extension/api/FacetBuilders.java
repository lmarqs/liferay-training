package com.liferay.blade.samples.guestbook.internal.search.extension.api;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public interface FacetBuilders {

	public DateRangeFacetBuilder dateRangeFacetBuilder();

}