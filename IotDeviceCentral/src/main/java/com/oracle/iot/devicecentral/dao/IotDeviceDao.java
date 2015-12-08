package com.oracle.iot.devicecentral.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IotDeviceDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<String> getAllDeviceNames() {
		String sql = "SELECT NAME as name FROM iot_device ORDER BY NAME DESC";
		return jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("name");
			}
		});
	}

	public boolean existsByName(String name) {
		String sql = "SELECT NAME FROM IOT_DEVICE WHERE name=?";
		List<Map<String, Object>> devices = jdbcTemplate.queryForList(sql, name);
		return devices.size() > 0;
	}

	public boolean updateDevice(String name, byte[] propertyFile, byte[] imageFile) {
		String sql = "UPDATE IOT_DEVICE SET device=?, picture=? WHERE name=?";
		int updated = jdbcTemplate.update(sql, propertyFile, imageFile, name);
		return updated != 0;
	}

	public boolean create(String name, byte[] propertyFile, byte[] imageFile) {
		String sql = "INSERT INTO IOT_DEVICE (ID,name,device,picture) VALUES (iot_device_seq.nextval,?,?,?)";
		int created = jdbcTemplate.update(sql, name, propertyFile, imageFile);
		return created != 0;
	}

	public List<Map<String, Object>> getDevicesByNames(List<String> names) {
		String sql = "SELECT NAME as name, DEVICE as device, PICTURE as picture FROM IOT_DEVICE WHERE name IN (:names)";
		Map<String, List<String>> namedParameters = Collections.singletonMap("names", names);
		return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForList(sql, namedParameters);
	}

	public boolean deleteByName(String name) {
		String sql = "DELETE FROM IOT_DEVICE WHERE name=?";
		return jdbcTemplate.update(sql, name) != 0;
	}

}
