package com.liferay.blade.samples.guestbook.internal.security.permission.resource;

import com.liferay.blade.samples.guestbook.constants.GuestbookConstants;
import com.liferay.blade.samples.guestbook.constants.GuestbookWebPortletKeys;
import com.liferay.blade.samples.guestbook.model.Entry;
import com.liferay.blade.samples.guestbook.service.EntryLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

@Component(immediate = true)
public class EntryModelResourcePermissionRegistrar {

    @Activate
    public void activate(BundleContext bundleContext) {

        Dictionary<String, Object> properties = new HashMapDictionary<>();

        properties.put("model.class.name", Entry.class.getName());

        _serviceRegistration = bundleContext.registerService(
                ModelResourcePermission.class,
                ModelResourcePermissionFactory.create(
                        Entry.class, Entry::getEntryId,
                        _entryLocalService::getEntry, _portletResourcePermission,
                        (modelResourcePermission, consumer) ->
                                consumer.accept(new StagingPermissionCheck(_stagingPermission))
                ),
                properties);

    }

    private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

    @Deactivate
    public void deactivate() {
        _serviceRegistration.unregister();
    }

    @Reference
    private EntryLocalService _entryLocalService;

    @Reference(target = "(resource.name=" + GuestbookConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission _portletResourcePermission;

    @Reference
    private StagingPermission _stagingPermission;

    private static class StagingPermissionCheck implements ModelResourcePermissionLogic<Entry> {

        @Override
        public Boolean contains(PermissionChecker permissionChecker, String name, Entry entry, String actionId) throws PortalException {

            return _stagingPermission.hasPermission(
                    permissionChecker, entry.getGroupId(),
                    Entry.class.getName(), entry.getGuestbookId(),
                    GuestbookWebPortletKeys.GUESTBOOK_WEB_PORTLET, actionId
            );

        }

        private StagingPermissionCheck(StagingPermission stagingPermission) {
            _stagingPermission = stagingPermission;
        }

        private final StagingPermission _stagingPermission;

    }

}