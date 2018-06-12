package br.com.objective.training.search;

import br.com.objective.training.model.Entry;
import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

@Component(
        immediate = true,
        property = "indexer.class.name=br.com.objective.training.model.Entry",
        service = ModelDocumentContributor.class
)
public class EntryModelDocumentContributor implements ModelDocumentContributor<Entry> {

    @Override
    public void contribute(Document document, Entry entry) {

        try {
            Locale defaultLocale = PortalUtil.getSiteDefaultLocale(entry.getGroupId());

            document.addDate(Field.MODIFIED_DATE, entry.getModifiedDate());
            document.addText("email", entry.getEmail());

            String localizedTitle = LocalizationUtil.getLocalizedName(Field.TITLE, defaultLocale.toString());
            String localizedMessage = LocalizationUtil.getLocalizedName(Field.CONTENT, defaultLocale.toString());

            document.addText(localizedTitle, entry.getName());
            document.addText(localizedMessage, entry.getMessage());

            long guestbookId = entry.getGuestbookId();

            Guestbook guestbook = _guestbookLocalService.getGuestbook(guestbookId);

            String guestbookName = guestbook.getName();

            String localizedGbName = LocalizationUtil.getLocalizedName("guestbookName", defaultLocale.toString());

            document.addText(localizedGbName, guestbookName);
        } catch (PortalException pe) {
            if (_log.isWarnEnabled()) {
                _log.warn("Unable to index entry " + entry.getEntryId(), pe);
            }
        }

    }


    @Reference
    private GuestbookLocalService _guestbookLocalService;

    private static final Log _log = LogFactoryUtil.getLog(EntryModelDocumentContributor.class);

}