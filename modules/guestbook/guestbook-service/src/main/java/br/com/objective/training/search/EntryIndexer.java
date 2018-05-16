package br.com.objective.training.search;

import br.com.objective.training.model.Entry;
import br.com.objective.training.service.EntryLocalService;
import br.com.objective.training.service.permission.EntryPermission;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.util.Locale;

@Component(
        immediate = true,
        service = Indexer.class
)
public class EntryIndexer extends BaseIndexer<Entry> {

    private static final String CLASS_NAME = Entry.class.getName();

    public EntryIndexer() {
        setDefaultSelectedFieldNames(
                Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.CONTENT,
                Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
                Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID
        );
        setPermissionAware(true);
        setFilterSearch(true);
    }

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    public boolean hasPermission(PermissionChecker permissionChecker, String entryClassName, long entryClassPK, String actionId) throws Exception {
        return EntryPermission.contains(permissionChecker, entryClassPK, ActionKeys.VIEW);
    }

    @Override
    public void postProcessContextBooleanFilter(BooleanFilter contextBooleanFilter, SearchContext searchContext) throws Exception {
        addStatus(contextBooleanFilter, searchContext);
    }

    @Override
    protected void doDelete(Entry entry) throws Exception {
        deleteDocument(entry.getCompanyId(), entry.getEntryId());
    }

    @Override
    protected Document doGetDocument(Entry entry) throws Exception {

        Document document = getBaseModelDocument(CLASS_NAME, entry);

        document.addDate(Field.MODIFIED_DATE, entry.getModifiedDate());

        Locale defaultLocale = PortalUtil.getSiteDefaultLocale(entry.getGroupId());

        String localizedTitle = LocalizationUtil.getLocalizedName(Field.TITLE, defaultLocale.toString());
        String localizedContent = LocalizationUtil.getLocalizedName(Field.CONTENT, defaultLocale.toString());

        document.addText(localizedTitle, entry.getName());
        document.addText(localizedContent, entry.getMessage());
        return document;
    }

    @Override
    protected Summary doGetSummary(Document document, Locale locale, String snippet, PortletRequest portletRequest, PortletResponse portletResponse) {
        Summary summary = createSummary(document);
        summary.setMaxContentLength(128);
        return summary;
    }

    @Override
    protected void doReindex(Entry entry) throws Exception {
        Document document = getDocument(entry);
        _indexWriterHelper.updateDocument(getSearchEngineId(), entry.getCompanyId(), document, isCommitImmediately());
    }

    @Override
    protected void doReindex(String className, long classPK) throws Exception {
        Entry entry = _entryLocalService.getEntry(classPK);
        doReindex(entry);
    }

    @Override
    protected void doReindex(String[] ids) throws Exception {
        long companyId = GetterUtil.getLong(ids[0]);
        _reindexEntries(companyId);
    }

    private void _reindexEntries(long companyId) throws PortalException {

        final IndexableActionableDynamicQuery query;
        query = _entryLocalService.getIndexableActionableDynamicQuery();

        query.setCompanyId(companyId);

        query.setPerformActionMethod((ActionableDynamicQuery.PerformActionMethod<Entry>) entry -> {
            try {
                Document document = getDocument(entry);
                query.addDocuments(document);
            } catch (PortalException pe) {
                if (_log.isWarnEnabled()) {
                    _log.warn("Unable to index entry " + entry.getEntryId(), pe);
                }
            }
        });

        query.setSearchEngineId(getSearchEngineId());
        query.performActions();
    }

    private static final Log _log = LogFactoryUtil.getLog(EntryIndexer.class);

    @Reference
    private EntryLocalService _entryLocalService;

    @Reference
    private IndexWriterHelper _indexWriterHelper;

}
