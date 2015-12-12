package com.oracle.iot.devicecentral.controller.bind;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DeviceUpload {
	private String deviceName;
	private byte[] file;
	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String name) {
		this.deviceName = name;
	}

}
