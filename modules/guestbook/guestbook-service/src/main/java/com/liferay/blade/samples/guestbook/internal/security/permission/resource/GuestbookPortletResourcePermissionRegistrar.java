package com.liferay.blade.samples.guestbook.internal.security.permission.resource;

import com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys;
import com.liferay.blade.samples.guestbook.constants.GuestbookConstants;
import com.liferay.blade.samples.guestbook.constants.GuestbookWebPortletKeys;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.kernel.util.HashMapDictionary;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

@Component(immediate = true)
public class GuestbookPortletResourcePermissionRegistrar {

    @Activate
    public void activate(BundleContext bundleContext) {

        Dictionary<String, Object> properties = new HashMapDictionary<>();

        properties.put("resource.name", GuestbookConstants.RESOURCE_NAME);

        _serviceRegistration = bundleContext.registerService(
                PortletResourcePermission.class,
                PortletResourcePermissionFactory.create(
                        GuestbookConstants.RESOURCE_NAME,
                        new StagedPortletPermissionLogic(_stagingPermission, GuestbookWebPortletKeys.GUESTBOOK_WEB_PORTLET),
                        new StagedPortletPermissionLogic(_stagingPermission, GuestbookAdminPortletKeys.GUESTBOOK_ADMIN_PORTLET)
                ),
                properties);

    }

    @Deactivate
    public void deactivate() {
        _serviceRegistration.unregister();
    }

    private ServiceRegistration<PortletResourcePermission> _serviceRegistration;

    @Reference
    private StagingPermission _stagingPermission;

}