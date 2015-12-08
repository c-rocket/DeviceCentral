package com.oracle.iot.devicecentral.controller.bind;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DeviceUpload {
	private String name;
	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
