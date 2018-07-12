package com.liferay.blade.samples.guestbook.internal.search.extension;

import com.liferay.blade.samples.guestbook.constants.search.GuestbookField;
import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.stream.Stream;

@Component(
        immediate = true,
        property = "indexer.class.name=com.liferay.blade.samples.guestbook.model.Guestbook",
        service = IndexerPostProcessor.class
)
public class GuestbookIndexerPostProcessor implements IndexerPostProcessor {

    @Activate
    public void activate(BundleContext bundleContext) {
        _log.info("activate");
    }

    @Deactivate
    public void deactivate() {
        _log.info("deactivate");
    }

    @Override
    public void postProcessContextBooleanFilter(BooleanFilter booleanFilter, SearchContext searchContext) {
        _log.info("postProcessContextBooleanFilter");
    }

    @Override
    public void postProcessContextQuery(BooleanQuery contextQuery, SearchContext searchContext) {
        _log.info("postProcessContextQuery");
    }

    @Override
    public void postProcessDocument(Document document, Object obj) {
        Guestbook guestbook = (Guestbook) obj;

        document.addText(GuestbookField.GUESTBOOK_NOTE, guestbook.getNote());
        document.addNumber(GuestbookField.GUESTBOOK_PRIORITY, guestbook.getPriority());
        document.addDate(GuestbookField.GUESTBOOK_EVENT_DATE, guestbook.getEventDate());
    }

    @Override
    public void postProcessFullQuery(BooleanQuery fullQuery, SearchContext searchContext) {
        _log.info("postProcessFullQuery");
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery searchQuery, BooleanFilter booleanFilter, SearchContext searchContext) {
        _keywordQueryContributor.contribute("", searchQuery, new KeywordQueryContributorHelper(searchContext));
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery searchQuery, SearchContext searchContext) {
        _log.info("postProcessSearchQuery");
    }

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String snippet) {
        _log.info("postProcessSummary");
    }

    @Reference
    protected QueryHelper queryHelper;

    @Reference(target = "(indexer.class.name=com.liferay.blade.samples.guestbook.model.Guestbook)")
    protected KeywordQueryContributor _keywordQueryContributor;

    private static Log _log = LogFactoryUtil.getLog(GuestbookIndexerPostProcessor.class);

    private static class KeywordQueryContributorHelper implements com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper {

        private final SearchContext _searchContext;

        private KeywordQueryContributorHelper(SearchContext searchContext) {
            _searchContext = searchContext;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public Stream<String> getSearchClassNamesStream() {
            return null;
        }

        @Override
        public SearchContext getSearchContext() {
            return _searchContext;
        }
    }
}