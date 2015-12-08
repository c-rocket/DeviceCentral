package com.oracle.iot.devicecentral.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class IotDeviceDao {

	public List<String> getAllDeviceNames() {
		String sql = "SELECT NAME FROM iot_device ORDER BY NAME DESC";
		return null;
	}

	public boolean existsByName(String name) {
		String sql = "SELECT NAME FROM iot_device WHERE name=? and filename=?";
		return false;
	}

	public void updateDevice(String name, MultipartFile propertyFile, MultipartFile imageFile) {
		String sql = "UPDATE iot_devices SET device=?, picture=? WHERE name=?";
		return;
	}

	public void create(String name, MultipartFile propertyFile, MultipartFile imageFile) {
		String sql = "INSERT INTO iot_devices (name,device,picture) VALUES(?,?,?)";
		return;
	}

	public List<Map<String, Object>> getDevicesByNames(List<String> names) {
		String sql = "SELECT * FROM iot_devices WHERE name IN (?)";
		return null;
	}

}
