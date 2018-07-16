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

package com.liferay.blade.samples.guestbook.service.impl;

import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.base.GuestbookServiceBaseImpl;
import com.liferay.blade.samples.guestbook.service.permission.GuestbookModelPermission;
import com.liferay.blade.samples.guestbook.service.permission.GuestbookPermission;
import com.liferay.blade.samples.guestbook.service.util.ActionKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the guestbook remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.blade.samples.guestbook.service.GuestbookService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GuestbookServiceBaseImpl
 * @see com.liferay.blade.samples.guestbook.service.GuestbookServiceUtil
 */
public class GuestbookServiceImpl extends GuestbookServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.blade.samples.guestbook.service.GuestbookServiceUtil} to access the guestbook remote service.
	 */
	public Guestbook addGuestbook(
			long userId, String name, String note, Integer priority,
			Date eventDate, ServiceContext serviceContext)
		throws PortalException {

		GuestbookModelPermission.check(
	getPermissionChecker(), serviceContext.getScopeGroupId(),
	ActionKeys.ADD_GUESTBOOK);

		return guestbookLocalService.addGuestbook(
	userId, name, note, priority, eventDate, serviceContext);
	}

	public Guestbook deleteGuestbook(
			long guestbookId, ServiceContext serviceContext)
		throws PortalException {

		GuestbookPermission.check(
	getPermissionChecker(), guestbookId, ActionKeys.DELETE);

		return guestbookLocalService.deleteGuestbook(
	guestbookId, serviceContext);
	}

	public List<Guestbook> getGuestbooks(long groupId) {
		return guestbookPersistence.filterFindByGroupId(groupId);
	}

	public List<Guestbook> getGuestbooks(long groupId, int start, int end) {
		return guestbookPersistence.filterFindByGroupId(groupId, start, end);
	}

	public int getGuestbooksCount(long groupId) {
		return guestbookPersistence.filterCountByGroupId(groupId);
	}

	public Guestbook updateGuestbook(
			long userId, long guestbookId, String name, String note,
			Integer priority, Date eventDate, ServiceContext serviceContext)
		throws PortalException {

		GuestbookPermission.check(
	getPermissionChecker(), guestbookId, ActionKeys.UPDATE);

		return guestbookLocalService.updateGuestbook(
	userId, guestbookId, name, note, priority, eventDate, serviceContext);
	}

}