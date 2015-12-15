package com.oracle.iot.devicecentral.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Transactional
@Ignore("DevCS does not appear to be able to call out")
public class IotDeviceDaoTest {
	@Resource
	private IotDeviceDao dao;

	@Test
	public void saveDevice() throws Exception {
		// setup
		String name = "zzSample";
		String industry = "Oil and Gas";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, industry, device, picture);

		// assert
		assertTrue(created);
	}

	@Test
	public void updateDevice() throws Exception {
		// setup
		String name = "zzSample";
		String industry = "Oil and Gas";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, industry, device, picture);
		boolean updated = dao.updateDevice(name, industry, device, picture);

		// assert
		assertTrue(created);
		assertTrue(updated);
	}

	@Test
	public void getDevice() throws Exception {
		// setup
		String name = "zzSample";
		String industry = "Oil and Gas";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		List<Map<String, Object>> originalNames = dao.getAllDeviceNames();
		boolean created = dao.create(name, industry, device, picture);
		List<Map<String, Object>> names = dao.getAllDeviceNames();
		Map<String, Object> actualDevice = dao.getDeviceByName(name);

		// assert
		assertTrue(created);
		assertNotNull(names);
		assertTrue(names.size() > 0);
		assertTrue(names.size() == originalNames.size() + 1);

		assertNotNull(actualDevice);
		assertEquals(name, actualDevice.get("NAME"));
		assertEquals(industry, actualDevice.get("INDUSTRY"));
		assertNotNull(actualDevice.get("DEVICE"));
		assertNotNull(actualDevice.get("PICTURE"));
	}

	@Test
	public void deleteDevice() throws Exception {
		// setup
		String name = "zzSample";
		String industry = "Oil and Gas";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, industry, device, picture);
		List<Map<String, Object>> names = dao.getAllDeviceNames();
		dao.deleteByName(name);
		List<Map<String, Object>> names2 = dao.getAllDeviceNames();

		// assert
		assertTrue(created);
		assertNotNull(names);
		assertTrue(names.size() > 0);

		assertTrue(created);
		assertNotNull(names2);
		assertTrue(names2.size() == names.size() - 1);
	}

	@Test
	public void updateDownloadCount() throws Exception {
		// setup
		String name = "zzSample";
		String industry = "Oil and Gas";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		dao.create(name, industry, device, picture);
		Map<String, Object> deviceByName = dao.getDeviceByName(name);
		dao.incrementDownloadCount((String) deviceByName.get("NAME"));
		Map<String, Object> actualDevice = dao.getDeviceByName(name);

		// assert
		assertEquals(0, ((BigDecimal) deviceByName.get("DOWNLOAD_COUNT")).intValue());
		assertEquals(1, ((BigDecimal) actualDevice.get("DOWNLOAD_COUNT")).intValue());
	}

	@Test
	public void updateRating() throws Exception {
		// setup
		String name = "zzSample";
		String industry = "Oil and Gas";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		String device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		String picture = createFromStream(pictureInputStream);

		// execute
		dao.create(name, industry, device, picture);
		Map<String, Object> deviceByName = dao.getDeviceByName(name);
		dao.addRating(name, 4);
		dao.addRating(name, 5);
		Map<String, Object> actualDevice = dao.getDeviceByName(name);

		// assert
		assertEquals(0d, ((BigDecimal) deviceByName.get("RATING")).doubleValue(), 0.001);
		assertEquals(4.5d, ((BigDecimal) actualDevice.get("RATING")).doubleValue(), 0.001);
	}

	private String createFromStream(InputStream inputStream) throws IOException {
		return IOUtils.toString(inputStream);
	}
}
