package com.oracle.iot.devicecentral.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.iot.devicecentral.dao.IotDeviceDao;
import com.oracle.iot.devicecentral.model.Status;

@Service
public class IoTDeviceService {

	@Resource
	IotDeviceDao dao;

	public List<String> getAllDevices() {
		return dao.getAllDeviceNames();
	}

	public Status save(String name, MultipartFile propertyFile, MultipartFile imageFile) {
		if (dao.existsByName(name)) {
			dao.updateDevice(name, propertyFile, imageFile);
			return Status.SUCCESS;
		} else {
			dao.create(name, propertyFile, imageFile);
			return Status.CREATED;
		}
	}

	public List<Map<String, Object>> getDevicesFiles(List<String> names) {
		List<Map<String, Object>> devices = dao.getDevicesByNames(names);
		return devices;
	}

}
