package com.liferay.blade.samples.guestbook.internal.search.extension;

import com.liferay.blade.samples.guestbook.constants.search.GuestbookField;
import com.liferay.blade.samples.guestbook.model.Entry;
import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

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
			long guestbookId = entry.getGuestbookId();

			Guestbook guestbook = _guestbookLocalService.getGuestbook(
	guestbookId);

			document.addText(
	GuestbookField.GUESTBOOK_NOTE, guestbook.getNote());
			document.addNumber(
	GuestbookField.GUESTBOOK_PRIORITY, guestbook.getPriority());
			document.addDate(
	GuestbookField.GUESTBOOK_EVENT_DATE, guestbook.getEventDate());

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