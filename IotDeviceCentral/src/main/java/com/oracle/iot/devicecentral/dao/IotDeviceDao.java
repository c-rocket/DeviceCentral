package com.oracle.iot.devicecentral.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IotDeviceDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getAllDeviceNames() {
		String sql = "SELECT NAME, INDUSTRY, RATING/DECODE(RATING_COUNT,0,1,RATING_COUNT) AS RATING, DOWNLOAD_COUNT FROM iot_device ORDER BY NAME DESC";
		return jdbcTemplate.queryForList(sql);
	}

	public boolean existsByName(String name) {
		String sql = "SELECT NAME FROM IOT_DEVICE WHERE name=?";
		List<Map<String, Object>> devices = jdbcTemplate.queryForList(sql, name);
		return devices.size() > 0;
	}

	public boolean updateDevice(String name, String industry, String propertyFile, String imageFile) {
		String sql = "UPDATE IOT_DEVICE SET device=?, picture=?, industry=? WHERE name=?";
		int updated = jdbcTemplate.update(sql, propertyFile, imageFile, industry, name);
		return updated != 0;
	}

	public boolean create(String name, String industry, String propertyFile, String imageFile) {
		String sql = "INSERT INTO IOT_DEVICE (ID,name,industry,device,picture) VALUES (iot_device_seq.nextval,?,?,?,?)";
		int created = jdbcTemplate.update(sql, name, industry, propertyFile, imageFile);
		return created != 0;
	}

	public Map<String, Object> getDeviceByName(String name) {
		String sql = "SELECT NAME, INDUSTRY, DOWNLOAD_COUNT, RATING/DECODE(RATING_COUNT,0,1,RATING_COUNT) as RATING, DEVICE, PICTURE FROM IOT_DEVICE WHERE name = ?";
		Map<String, Object> device = jdbcTemplate.queryForMap(sql, name);
		return device;
	}

	public boolean incrementDownloadCount(String name) {
		String sql = "UPDATE IOT_DEVICE SET download_count=(SELECT download_count+1 FROM IOT_DEVICE WHERE name=?) WHERE name=?";
		int updated = jdbcTemplate.update(sql, name, name);
		return updated != 0;
	}

	public boolean addRating(String name, int rating) {
		String sql = "UPDATE IOT_DEVICE SET rating=(SELECT rating+? FROM IOT_DEVICE WHERE name=?), rating_count=(SELECT rating_count+1 FROM IOT_DEVICE WHERE name=?) WHERE name=?";
		int updated = jdbcTemplate.update(sql, rating, name, name, name);
		return updated != 0;
	}

	public boolean deleteByName(String name) {
		String sql = "DELETE FROM IOT_DEVICE WHERE name=?";
		return jdbcTemplate.update(sql, name) != 0;
	}

}
