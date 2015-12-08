package com.oracle.iot.devicecentral.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class);

	public static File convert(MultipartFile multipartFile) {
		if (multipartFile.isEmpty() || multipartFile == null) {
			return null;
		}
		try {
			File convFile = new File(multipartFile.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(multipartFile.getBytes());
			fos.close();
			return convFile;
		} catch (Exception e) {
			logger.error("Error converting multipart to file", e);
			return null;
		}
	}

}
