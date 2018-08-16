package com.liferay.blade.samples.extension;

import java.util.Iterator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

@Component(immediate = true)
public class Component1 {

	@Activate
	protected void activate(BundleContext bundleContext) {
		ServiceTrackerList<BaseService, BaseService> serviceTrackerList;
		
		serviceTrackerList = ServiceTrackerListFactory.open(bundleContext,BaseService.class);

		Iterator<BaseService> iterator = serviceTrackerList.iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

}