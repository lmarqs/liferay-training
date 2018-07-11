package com.liferay.blade.samples.guestbook.internal.search.extension;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.search.facet.Facet;

@ProviderType
public interface DateRangeFacet extends Facet {
    public void setFrom(String from);

    public void setTo(String to);
}