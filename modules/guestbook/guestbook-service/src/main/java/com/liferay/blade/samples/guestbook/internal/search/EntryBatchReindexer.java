package com.liferay.blade.samples.guestbook.internal.search;

public interface EntryBatchReindexer {

	public void reindex(long guestbookId, long companyId);

}