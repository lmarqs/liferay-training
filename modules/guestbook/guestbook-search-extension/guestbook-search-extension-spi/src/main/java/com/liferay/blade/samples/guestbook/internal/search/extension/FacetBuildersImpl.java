package com.liferay.blade.samples.guestbook.internal.search.extension;

import org.osgi.service.component.annotations.Component;

@Component(
        immediate = true,
        service = FacetBuilders.class
)
public class FacetBuildersImpl implements FacetBuilders {

    @Override
    public DateRangeFacetBuilder dateRangeFacetBuilder() {
        return new DateRangeFacetBuilderImpl();
    }

}
