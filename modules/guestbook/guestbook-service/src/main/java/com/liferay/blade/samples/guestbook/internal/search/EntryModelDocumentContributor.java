package com.liferay.blade.samples.guestbook.internal.search;

import com.liferay.blade.samples.guestbook.constants.search.EntryField;
import com.liferay.blade.samples.guestbook.constants.search.GuestbookField;
import com.liferay.blade.samples.guestbook.model.Entry;
import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = "indexer.class.name=com.liferay.blade.samples.guestbook.model.Entry",
		service = ModelDocumentContributor.class
)
public class EntryModelDocumentContributor
	implements ModelDocumentContributor<Entry> {

	@Override
	public void contribute(Document document, Entry entry) {
		try {
			Locale defaultLocale = PortalUtil.getSiteDefaultLocale(
	entry.getGroupId());

			document.addDate(Field.MODIFIED_DATE, entry.getModifiedDate());
			document.addText(EntryField.ENTRY_EMAIL, entry.getEmail());

			String localizedTitle = LocalizationUtil.getLocalizedName(
	Field.TITLE, defaultLocale.toString());
			String localizedContent = LocalizationUtil.getLocalizedName(
	Field.CONTENT, defaultLocale.toString());

			document.addText(localizedTitle, entry.getName());
			document.addText(localizedContent, entry.getMessage());

			long guestbookId = entry.getGuestbookId();

			Guestbook guestbook = _guestbookLocalService.getGuestbook(
	guestbookId);

			String guestbookName = guestbook.getName();

			String localizedGbName = LocalizationUtil.getLocalizedName(
	GuestbookField.GUESTBOOK_NAME, defaultLocale.toString());

			document.addText(localizedGbName, guestbookName);
		} catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index entry " + entry.getEntryId(), pe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
	EntryModelDocumentContributor.class);

	@Reference
	private GuestbookLocalService _guestbookLocalService;

}