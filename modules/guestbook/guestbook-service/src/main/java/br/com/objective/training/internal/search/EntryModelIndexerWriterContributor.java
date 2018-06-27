package br.com.objective.training.internal.search;

import br.com.objective.training.model.Entry;
import br.com.objective.training.service.EntryLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(
        immediate = true,
        property = "indexer.class.name=br.com.objective.training.model.Entry",
        service = ModelIndexerWriterContributor.class
)
public class EntryModelIndexerWriterContributor implements ModelIndexerWriterContributor<Entry> {

    @Override
    public void customize(BatchIndexingActionable batchIndexingActionable, ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

        batchIndexingActionable.setPerformActionMethod((Entry entry) -> {
            Document document = modelIndexerWriterDocumentHelper.getDocument(entry);

            batchIndexingActionable.addDocuments(document);
        });

    }

    @Override
    public BatchIndexingActionable getBatchIndexingActionable() {
        return dynamicQueryBatchIndexingActionableFactory.getBatchIndexingActionable(entryLocalService.getIndexableActionableDynamicQuery());
    }

    @Override
    public long getCompanyId(Entry entry) {
        return entry.getCompanyId();
    }

    @Reference
    protected EntryLocalService entryLocalService;

    @Reference
    protected DynamicQueryBatchIndexingActionableFactory dynamicQueryBatchIndexingActionableFactory;

}