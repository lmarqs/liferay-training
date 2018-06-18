/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package br.com.objective.training.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Entry. This utility wraps
 * {@link br.com.objective.training.service.impl.EntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see EntryService
 * @see br.com.objective.training.service.base.EntryServiceBaseImpl
 * @see br.com.objective.training.service.impl.EntryServiceImpl
 * @generated
 */
@ProviderType
public class EntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link br.com.objective.training.service.impl.EntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static br.com.objective.training.model.Entry addEntry(long userId,
		long guestbookId, String name, String email, String message,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(userId, guestbookId, name, email, message,
			serviceContext);
	}

	public static br.com.objective.training.model.Entry deleteEntry(
		long entryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteEntry(entryId, serviceContext);
	}

	public static java.util.List<br.com.objective.training.model.Entry> getEntries(
		long groupId, long guestbookId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, guestbookId);
	}

	public static java.util.List<br.com.objective.training.model.Entry> getEntries(
		long groupId, long guestbookId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, guestbookId, start, end);
	}

	public static int getEntriesCount(long groupId, long guestbookId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(groupId, guestbookId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static br.com.objective.training.model.Entry updateEntry(
		long userId, long guestbookId, long entryId, String name, String email,
		String message,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, guestbookId, entryId, name, email,
			message, serviceContext);
	}

	public static EntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<EntryService, EntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(EntryService.class);

		ServiceTracker<EntryService, EntryService> serviceTracker = new ServiceTracker<EntryService, EntryService>(bundle.getBundleContext(),
				EntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}