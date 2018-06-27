package br.com.objective.training.internal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.Locale;

@Component(
        immediate = true,
        property = "indexer.class.name=br.com.objective.training.model.Entry",
        service = IndexerPostProcessor.class
)
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