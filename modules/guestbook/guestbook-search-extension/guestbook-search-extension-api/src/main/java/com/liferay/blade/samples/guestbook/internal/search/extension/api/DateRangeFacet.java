package com.liferay.blade.samples.guestbook.internal.search.extension.api;

import com.liferay.portal.search.facet.Facet;

//@ProviderType
public interface DateRangeFacet extends Facet {

	public void setFrom(String from);

	public void setTo(String to);

}