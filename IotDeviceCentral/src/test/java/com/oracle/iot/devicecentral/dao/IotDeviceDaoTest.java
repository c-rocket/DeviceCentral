package com.oracle.iot.devicecentral.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Transactional
public class IotDeviceDaoTest {
	@Resource
	private IotDeviceDao dao;

	@Test
	public void saveDevice() throws Exception {
		// setup
		String name = "Sample";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, device, picture);

		// assert
		assertTrue(created);
	}

	@Test
	public void updateDevice() throws Exception {
		// setup
		String name = "Sample";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, device, picture);
		boolean updated = dao.updateDevice(name, device, picture);

		// assert
		assertTrue(created);
		assertTrue(updated);
	}

	@Test
	public void getDevice() throws Exception {
		// setup
		String name = "Sample";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, device, picture);
		List<String> names = dao.getAllDeviceNames();
		Map<String, Object> actualDevice = dao.getDeviceByName(name);

		// assert
		assertTrue(created);
		assertNotNull(names);
		assertTrue(names.size() > 0);
		assertTrue(names.contains("Sample"));

		assertNotNull(actualDevice);
		assertEquals(name, actualDevice.get("name"));
		assertNotNull(actualDevice.get("device"));
		assertNotNull(actualDevice.get("picture"));
	}

	@Test
	public void deleteDevice() throws Exception {
		// setup
		String name = "Sample";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, device, picture);
		List<String> names = dao.getAllDeviceNames();
		dao.deleteByName(name);
		List<String> names2 = dao.getAllDeviceNames();

		// assert
		assertTrue(created);
		assertNotNull(names);
		assertTrue(names.size() > 0);
		assertTrue(names.contains("Sample"));

		assertTrue(created);
		assertNotNull(names2);
		assertTrue(names2.size() == names.size() - 1);
	}

	private String createFromStream(InputStream inputStream) throws IOException {
		return IOUtils.toString(inputStream);
	}
}
