/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package br.com.objective.training.service.impl;

import br.com.objective.training.model.Entry;
import br.com.objective.training.service.base.EntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * The implementation of the entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link br.com.objective.training.service.EntryService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EntryServiceBaseImpl
 * @see br.com.objective.training.service.EntryServiceUtil
 */
public class EntryServiceImpl extends EntryServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Always use {@link br.com.objective.training.service.EntryServiceUtil} to access the entry remote service.
     */

    public Entry addEntry(long userId, long guestbookId, String name, String email, String message, ServiceContext serviceContext) throws PortalException, SystemException {
        return entryLocalService.addEntry(userId, guestbookId, name, email, message, serviceContext);
    }

    public Entry deleteEntry(long entryId, ServiceContext serviceContext) throws PortalException, SystemException {
        return entryLocalService.deleteEntry(entryId, serviceContext);
    }

    public List<Entry> getEntries(long groupId, long guestbookId) throws SystemException {
        return entryLocalService.getEntries(groupId, guestbookId);
    }

    public List<Entry> getEntries(long groupId, long guestbookId, int start, int end) throws SystemException {
        return entryLocalService.getEntries(groupId, guestbookId, start, end);
    }

    public int getEntriesCount(long groupId, long guestbookId) throws SystemException {
        return entryLocalService.getEntriesCount(groupId, guestbookId);
    }

    public Entry updateEntry(long userId, long guestbookId, long entryId, String name, String email, String message, ServiceContext serviceContext) throws PortalException, SystemException {
        return entryLocalService.updateEntry(userId, guestbookId, entryId, name, email, message, serviceContext);
    }
}