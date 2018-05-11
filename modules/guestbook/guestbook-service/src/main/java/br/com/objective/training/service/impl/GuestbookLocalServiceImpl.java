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

import br.com.objective.training.exception.GuestbookNameException;
import br.com.objective.training.model.Entry;
import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.base.GuestbookLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the guestbook local service.
 * <p>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link br.com.objective.training.service.GuestbookLocalService} interface.
 * <p>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GuestbookLocalServiceBaseImpl
 * @see br.com.objective.training.service.GuestbookLocalServiceUtil
 */
public class GuestbookLocalServiceImpl extends GuestbookLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Always use {@link br.com.objective.training.service.GuestbookLocalServiceUtil} to access the guestbook local service.
     */

    public Guestbook addGuestbook(long userId, String name, ServiceContext serviceContext) throws PortalException {

        long groupId = serviceContext.getScopeGroupId();

        User user = userLocalService.getUserById(userId);

        Date now = new Date();

        validate(name);

        long guestbookId = counterLocalService.increment();

        Guestbook guestbook = guestbookPersistence.create(guestbookId);

        guestbook.setUuid(serviceContext.getUuid());
        guestbook.setUserId(userId);
        guestbook.setGroupId(groupId);
        guestbook.setCompanyId(user.getCompanyId());
        guestbook.setUserName(user.getFullName());
        guestbook.setCreateDate(serviceContext.getCreateDate(now));
        guestbook.setModifiedDate(serviceContext.getModifiedDate(now));
        guestbook.setName(name);
        guestbook.setExpandoBridgeAttributes(serviceContext);

        guestbookPersistence.update(guestbook);

        return guestbook;

    }

    public Guestbook updateGuestbook(long userId, long guestbookId, String name, ServiceContext serviceContext) throws PortalException, SystemException {

        Date now = new Date();

        validate(name);

        Guestbook guestbook = getGuestbook(guestbookId);

        User user = userLocalService.getUser(userId);

        guestbook.setUserId(userId);
        guestbook.setUserName(user.getFullName());
        guestbook.setModifiedDate(serviceContext.getModifiedDate(now));
        guestbook.setName(name);
        guestbook.setExpandoBridgeAttributes(serviceContext);

        guestbookPersistence.update(guestbook);

        return guestbook;
    }

    public Guestbook deleteGuestbook(long guestbookId, ServiceContext serviceContext) throws PortalException, SystemException {

        Guestbook guestbook = getGuestbook(guestbookId);

        List<Entry> entries = entryLocalService.getEntries(serviceContext.getScopeGroupId(), guestbookId);

        for (Entry entry : entries) {
            entryLocalService.deleteEntry(entry.getEntryId());
        }

        guestbook = deleteGuestbook(guestbook);

        return guestbook;
    }

    public List<Guestbook> getGuestbooks(long groupId) {
        return guestbookPersistence.findByGroupId(groupId);
    }

    public List<Guestbook> getGuestbooks(long groupId, int start, int end, OrderByComparator<Guestbook> obc) {
        return guestbookPersistence.findByGroupId(groupId, start, end, obc);
    }

    public List<Guestbook> getGuestbooks(long groupId, int start, int end) {
        return guestbookPersistence.findByGroupId(groupId, start, end);
    }

    public int getGuestbooksCount(long groupId) {
        return guestbookPersistence.countByGroupId(groupId);
    }

    protected void validate(String name) throws PortalException {
        if (Validator.isNull(name)) {
            throw new GuestbookNameException();
        }
    }
}