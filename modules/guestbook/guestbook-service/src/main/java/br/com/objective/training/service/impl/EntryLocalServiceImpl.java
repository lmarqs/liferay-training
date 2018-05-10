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

import br.com.objective.training.exception.EntryEmailException;
import br.com.objective.training.exception.EntryMessageException;
import br.com.objective.training.exception.EntryNameException;
import br.com.objective.training.model.Entry;
import br.com.objective.training.service.base.EntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

/**
 * The implementation of the entry local service.
 * <p>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link br.com.objective.training.service.EntryLocalService} interface.
 * <p>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EntryLocalServiceBaseImpl
 * @see br.com.objective.training.service.EntryLocalServiceUtil
 */
public class EntryLocalServiceImpl extends EntryLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Always use {@link br.com.objective.training.service.EntryLocalServiceUtil} to access the entry local service.
     */

    public Entry addEntry(
            long userId, long guestbookId, String name, String email,
            String message, ServiceContext serviceContext)
            throws PortalException {

        long groupId = serviceContext.getScopeGroupId();

        User user = userLocalService.getUserById(userId);

        Date now = new Date();

        validate(name, email, message);

        long entryId = counterLocalService.increment();

        Entry entry = entryPersistence.create(entryId);

        entry.setUuid(serviceContext.getUuid());
        entry.setUserId(userId);
        entry.setGroupId(groupId);
        entry.setCompanyId(user.getCompanyId());
        entry.setUserName(user.getFullName());
        entry.setCreateDate(serviceContext.getCreateDate(now));
        entry.setModifiedDate(serviceContext.getModifiedDate(now));
        entry.setExpandoBridgeAttributes(serviceContext);
        entry.setGuestbookId(guestbookId);
        entry.setName(name);
        entry.setEmail(email);
        entry.setMessage(message);

        entryPersistence.update(entry);

        return entry;
    }

    public Entry updateEntry(
            long userId, long guestbookId, long entryId, String name, String email,
            String message, ServiceContext serviceContext)
            throws PortalException, SystemException {

        Date now = new Date();

        validate(name, email, message);

        Entry entry = getEntry(entryId);

        User user = userLocalService.getUserById(userId);

        entry.setUserId(userId);
        entry.setUserName(user.getFullName());
        entry.setModifiedDate(serviceContext.getModifiedDate(now));
        entry.setName(name);
        entry.setEmail(email);
        entry.setMessage(message);
        entry.setExpandoBridgeAttributes(serviceContext);

        entryPersistence.update(entry);

        return entry;
    }

    public Entry deleteEntry(long entryId, ServiceContext serviceContext)
            throws PortalException {

        Entry entry = getEntry(entryId);

        entry = deleteEntry(entryId);

        return entry;
    }

    public List<Entry> getEntries(long groupId, long guestbookId) {
        return entryPersistence.findByG_G(groupId, guestbookId);
    }

    public List<Entry> getEntries(long groupId, long guestbookId, int start, int end) throws SystemException {
        return entryPersistence.findByG_G(groupId, guestbookId, start, end);
    }

    public List<Entry> getEntries(long groupId, long guestbookId, int start, int end, OrderByComparator<Entry> obc) {
        return entryPersistence.findByG_G(groupId, guestbookId, start, end, obc);
    }

    public int getEntriesCount(long groupId, long guestbookId) {
        return entryPersistence.countByG_G(groupId, guestbookId);
    }

    protected void validate(String name, String email, String entry)
            throws PortalException {

        if (Validator.isNull(name)) {
            throw new EntryNameException();
        }

        if (!Validator.isEmailAddress(email)) {
            throw new EntryEmailException();
        }

        if (Validator.isNull(entry)) {
            throw new EntryMessageException();
        }
    }
}