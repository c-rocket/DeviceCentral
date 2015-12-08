package com.oracle.iot.devicecentral.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.iot.devicecentral.controller.bind.DeviceUpload;
import com.oracle.iot.devicecentral.model.Response;
import com.oracle.iot.devicecentral.model.Status;
import com.oracle.iot.devicecentral.service.IoTDeviceService;

@Controller
public class DeviceController {

	@Resource
	IoTDeviceService deviceService;

	@RequestMapping(value = "/devices/list", method = RequestMethod.GET)
	public @ResponseBody List<String> setupPage(Model model) {
		return deviceService.getAllDevices();
	}

	@RequestMapping(value = "/device/save", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> uploadDeviceGet() {
		return new Response(Status.SAVE_USAGE).map();
	}

	@RequestMapping(value = "/device/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadDevicePost(@ModelAttribute("device") DeviceUpload device) {
		MultipartFile propertyFile = device.getFiles().get(0);
		MultipartFile imageFile = device.getFiles().get(1);
		if (!propertyFile.isEmpty()) {
			Status status = deviceService.save(device.getName(), propertyFile, imageFile);
			return new Response(status).map();
		} else {
			return new Response(Status.BAD_PARAMS).map();
		}
	}

	@RequestMapping(value = "/device/show", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> downloadDevices(@RequestParam String[] names) {
		return deviceService.getDevicesFiles(Arrays.asList(names));
	}
}
