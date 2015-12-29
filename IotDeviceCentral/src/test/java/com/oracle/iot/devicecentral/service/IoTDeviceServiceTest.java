package com.oracle.iot.devicecentral.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.oracle.iot.devicecentral.dao.IotDeviceDao;
import com.oracle.iot.devicecentral.service.IoTDeviceService;

@RunWith(MockitoJUnitRunner.class)
public class IoTDeviceServiceTest {
	@Mock
	private IotDeviceDao dao;

	@InjectMocks
	private IoTDeviceService service;

	@Test
	public void getAllDevices() throws Exception {
		// setup
		List<Map<String, Object>> names = Arrays.asList(createMapItem("Test"), createMapItem("Names"),
				createMapItem("Here"));
		Mockito.when(dao.getAllDeviceNames()).thenReturn(names);

		// execute
		List<Map<String, Object>> allDevices = service.getAllDevices();

		// assert
		assertEquals(names.size(), allDevices.size());
		assertEquals(allDevices.get(0).get("NAME"), "Test");
		assertEquals(allDevices.get(1).get("NAME"), "Names");
		assertEquals(allDevices.get(2).get("NAME"), "Here");
	}

	private Map<String, Object> createMapItem(String string) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("NAME", string);
		return map;
	}

	@Test
	public void save_nameExists() throws Exception {
		// setup
		String name = "test";
		String industry = "General";
		String propertyFile = "propertyFile";
		String imageFile = "imageFile";
		Mockito.when(dao.existsByName(name)).thenReturn(true);

		// execute
		service.save(name, industry, propertyFile, imageFile);

		// assert
		Mockito.verify(dao, Mockito.times(1)).updateDevice(name, industry, propertyFile, imageFile);
		Mockito.verify(dao, Mockito.never()).create(name, industry, propertyFile, imageFile);
	}

	@Test
	public void save_nameDoesNotExist() throws Exception {
		// setup
		String name = "test";
		String industry = "General";
		String propertyFile = "propertyFile";
		String imageFile = "imageFile";
		Mockito.when(dao.existsByName(name)).thenReturn(false);

		// execute
		service.save(name, industry, propertyFile, imageFile);

		// assert
		Mockito.verify(dao, Mockito.never()).updateDevice(name, industry, propertyFile, imageFile);
		Mockito.verify(dao, Mockito.times(1)).create(name, industry, propertyFile, imageFile);
	}
}
