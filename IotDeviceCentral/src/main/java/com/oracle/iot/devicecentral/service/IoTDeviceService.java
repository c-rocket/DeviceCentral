package com.oracle.iot.devicecentral.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.iot.devicecentral.dao.IotDeviceDao;
import com.oracle.iot.devicecentral.model.Status;

@Service
@Transactional
public class IoTDeviceService {

	private static Logger logger = Logger.getLogger(IoTDeviceService.class);

	@Resource
	IotDeviceDao dao;

	public List<String> getAllDevices() {
		try {
			return dao.getAllDeviceNames();
		} catch (Exception e) {
			logger.error("Can't pull up names", e);
			return new ArrayList<String>();
		}
	}

	public Status save(String name, String propertyFile, String imageFile) {
		if (imageFile == null || imageFile.length() == 0) {
			imageFile = loadDefaultWidget();
		}
		try {
			if (dao.existsByName(name)) {
				dao.updateDevice(name, propertyFile, imageFile);
				return Status.SUCCESS;
			} else {
				dao.create(name, propertyFile, imageFile);
				return Status.CREATED;
			}
		} catch (Exception e) {
			logger.error("Error saving file", e);
			return Status.ERROR;
		}
	}

	private String loadDefaultWidget() {
		try {
			InputStream widgetStream = this.getClass().getClassLoader().getResourceAsStream("default/widget.png");
			String str = IOUtils.toString(widgetStream);
			widgetStream.close();
			return str;
		} catch (Exception e) {
			logger.error("Error loading widget", e);
			return null;
		}
	}

	public Map<String, Object> getDeviceFiles(String name) {
		return dao.getDeviceByName(name);
	}

	public void deleteDeviceByName(String name) {
		dao.deleteByName(name);
	}

}
