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

package com.liferay.blade.samples.guestbook.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.blade.samples.guestbook.service.EntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link EntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.blade.samples.guestbook.model.EntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.blade.samples.guestbook.model.Entry}, that is translated to a
 * {@link com.liferay.blade.samples.guestbook.model.EntrySoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EntryServiceHttp
 * @see com.liferay.blade.samples.guestbook.model.EntrySoap
 * @see EntryServiceUtil
 * @generated
 */
@ProviderType
public class EntryServiceSoap {
	public static com.liferay.blade.samples.guestbook.model.EntrySoap addEntry(
		long userId, long guestbookId, String name, String email,
		String message,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.blade.samples.guestbook.model.Entry returnValue = EntryServiceUtil.addEntry(userId,
					guestbookId, name, email, message, serviceContext);

			return com.liferay.blade.samples.guestbook.model.EntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blade.samples.guestbook.model.EntrySoap deleteEntry(
		long entryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.blade.samples.guestbook.model.Entry returnValue = EntryServiceUtil.deleteEntry(entryId,
					serviceContext);

			return com.liferay.blade.samples.guestbook.model.EntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blade.samples.guestbook.model.EntrySoap[] getEntries(
		long groupId, long guestbookId) throws RemoteException {
		try {
			java.util.List<com.liferay.blade.samples.guestbook.model.Entry> returnValue =
				EntryServiceUtil.getEntries(groupId, guestbookId);

			return com.liferay.blade.samples.guestbook.model.EntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blade.samples.guestbook.model.EntrySoap[] getEntries(
		long groupId, long guestbookId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.blade.samples.guestbook.model.Entry> returnValue =
				EntryServiceUtil.getEntries(groupId, guestbookId, start, end);

			return com.liferay.blade.samples.guestbook.model.EntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(long groupId, long guestbookId)
		throws RemoteException {
		try {
			int returnValue = EntryServiceUtil.getEntriesCount(groupId,
					guestbookId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.blade.samples.guestbook.model.EntrySoap updateEntry(
		long userId, long guestbookId, long entryId, String name, String email,
		String message,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.blade.samples.guestbook.model.Entry returnValue = EntryServiceUtil.updateEntry(userId,
					guestbookId, entryId, name, email, message, serviceContext);

			return com.liferay.blade.samples.guestbook.model.EntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntryServiceSoap.class);
}