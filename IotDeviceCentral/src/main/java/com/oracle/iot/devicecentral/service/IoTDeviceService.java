package com.oracle.iot.devicecentral.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.oracle.iot.devicecentral.dao.IotDeviceDao;
import com.oracle.iot.devicecentral.model.Status;

@Service
public class IoTDeviceService {

	private static Logger logger = Logger.getLogger(IoTDeviceService.class);

	@Resource
	IotDeviceDao dao;

	public List<String> getAllDevices() {
		return dao.getAllDeviceNames();
	}

	public Status save(String name, File propertyFile, File imageFile) {
		if (imageFile == null) {
			imageFile = loadDefaultWidget();
		}
		try {
			byte[] deviceAr = IOUtils.toByteArray(new FileInputStream(propertyFile));
			byte[] imageAr = IOUtils.toByteArray(new FileInputStream(imageFile));
			if (dao.existsByName(name)) {
				dao.updateDevice(name, deviceAr, imageAr);
				return Status.SUCCESS;
			} else {
				dao.create(name, deviceAr, imageAr);
				return Status.CREATED;
			}
		} catch (Exception e) {
			logger.error("Error saving file", e);
			return Status.ERROR;
		}
	}

	private File loadDefaultWidget() {
		try {
			InputStream widgetStream = this.getClass().getClassLoader().getResourceAsStream("default/widget.png");
			File file = File.createTempFile("widget", ".png");
			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(widgetStream, outputStream);
			widgetStream.close();
			outputStream.close();
			return file;
		} catch (Exception e) {
			logger.error("Error loading widget", e);
			return null;
		}
	}

	public List<Map<String, Object>> getDevicesFiles(List<String> names) {
		List<Map<String, Object>> devices = dao.getDevicesByNames(names);
		return devices;
	}

}
