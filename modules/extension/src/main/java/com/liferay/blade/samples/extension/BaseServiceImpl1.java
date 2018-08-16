package com.liferay.blade.samples.extension;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = BaseService.class)
public class BaseServiceImpl1 implements BaseService {

	@Override
	public String toString() {
		return "BaseServiceImpl1";
	}
}