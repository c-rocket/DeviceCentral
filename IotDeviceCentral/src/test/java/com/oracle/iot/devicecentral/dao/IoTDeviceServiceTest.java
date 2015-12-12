package com.oracle.iot.devicecentral.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
		List<String> names = Arrays.asList("Test", "Names", "Here");
		Mockito.when(dao.getAllDeviceNames()).thenReturn(names);

		// execute
		List<String> allDevices = service.getAllDevices();

		// assert
		assertEquals(names.size(), allDevices.size());
		assertTrue(allDevices.contains("Test"));
		assertTrue(allDevices.contains("Names"));
		assertTrue(allDevices.contains("Here"));
	}

	@Test
	public void save_nameExists() throws Exception {
		// setup
		String name = "test";
		String propertyFile = "propertyFile";
		String imageFile = "imageFile";
		Mockito.when(dao.existsByName(name)).thenReturn(true);

		// execute
		service.save(name, propertyFile, imageFile);

		// assert
		Mockito.verify(dao, Mockito.times(1)).updateDevice(name, propertyFile, imageFile);
		Mockito.verify(dao, Mockito.never()).create(name, propertyFile, imageFile);
	}

	@Test
	public void save_nameDoesNotExist() throws Exception {
		// setup
		String name = "test";
		String propertyFile = "propertyFile";
		String imageFile = "imageFile";
		Mockito.when(dao.existsByName(name)).thenReturn(false);

		// execute
		service.save(name, propertyFile, imageFile);

		// assert
		Mockito.verify(dao, Mockito.never()).updateDevice(name, propertyFile, imageFile);
		Mockito.verify(dao, Mockito.times(1)).create(name, propertyFile, imageFile);
	}
}
