package com.oracle.iot.devicecentral.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class IotDeviceDaoTest {
	@Resource
	private IotDeviceDao dao;

	@Test
	public void saveDevice() throws Exception {
		// setup
		String name = "Sample";
		InputStream deviceInputStream = this.getClass().getClassLoader()
				.getResourceAsStream("deviceLoad/template.properties");
		byte[] device = createFromStream(deviceInputStream);

		InputStream pictureInputStream = this.getClass().getClassLoader().getResourceAsStream("deviceLoad/widget.png");
		byte[] picture = createFromStream(pictureInputStream);

		// execute
		boolean created = dao.create(name, device, picture);

		// assert
		assertTrue(created);

		dao.deleteByName(name);
	}

	private byte[] createFromStream(InputStream inputStream) throws IOException {
		return IOUtils.toByteArray(inputStream);
	}
}
