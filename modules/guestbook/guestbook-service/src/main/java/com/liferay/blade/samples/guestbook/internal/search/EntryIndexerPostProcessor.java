package com.liferay.blade.samples.guestbook.internal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.Locale;

/**
 * To test this class remove the @Component annotation from the EntrySearchRegistrar class.
 * As it is done on this commit: 8162300c2735c93e7214c21e2e7dbfdb943d4b81
 */
@Component(
        immediate = true,
        property = "indexer.class.name=com.liferay.blade.samples.guestbook.model.Entry",
        service = IndexerPostProcessor.class
)
@Deprecated
public class EntryIndexerPostProcessor implements IndexerPostProcessor {

    @Activate
    public void activate(BundleContext bundleContext) {
        _log.info( "activate");
    }

    @Deactivate
    public void deactivate() {
        _log.info( "deactivate");
    }

    @Override
    public void postProcessContextBooleanFilter(BooleanFilter booleanFilter, SearchContext searchContext) throws Exception {
        _log.info( "postProcessContextBooleanFilter");
    }

    @Override
    public void postProcessContextQuery(BooleanQuery contextQuery, SearchContext searchContext) throws Exception {
        _log.info( "postProcessContextQuery");
    }

    @Override
    public void postProcessDocument(Document document, Object obj) throws Exception {
        _log.info( "postProcessDocument");
    }

    @Override
    public void postProcessFullQuery(BooleanQuery fullQuery, SearchContext searchContext) throws Exception {
        _log.info( "postProcessFullQuery");
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery searchQuery, BooleanFilter booleanFilter, SearchContext searchContext) throws Exception {
        _log.info( "postProcessSearchQuery");
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery searchQuery, SearchContext searchContext) throws Exception {
        _log.info( "postProcessSearchQuery");
    }

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String snippet) {
        _log.info( "postProcessSummary");
    }

    private static Log _log = LogFactoryUtil.getLog(EntryIndexerPostProcessor.class);
}