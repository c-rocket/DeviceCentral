package com.oracle.iot.devicecentral.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.iot.devicecentral.model.Response;
import com.oracle.iot.devicecentral.model.Status;
import com.oracle.iot.devicecentral.service.IoTDeviceService;

@Controller
public class DeviceController {

	@Resource
	IoTDeviceService deviceService;

	@RequestMapping(value = "/devices/list", method = RequestMethod.GET)
	public @ResponseBody List<String> setupPage() {
		return deviceService.getAllDevices();
	}

	@RequestMapping(value = "/device/save", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> uploadDeviceGet() {
		return new Response(Status.SAVE_USAGE).map();
	}

	@RequestMapping(value = "/device/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadDevicePost(
			@RequestParam(required = false, name = "deviceName") String deviceName,
			@RequestParam(required = false, name = "deviceFile") String deviceFile,
			@RequestParam(required = false, name = "imageFile") String imageFile) {
		if (deviceName != null && deviceFile != null) {
			Status status = deviceService.save(deviceName, deviceFile, imageFile);
			return new Response(status).map();
		} else {
			return new Response(Status.BAD_PARAMS).map();
		}
	}

	@RequestMapping(value = "/device/show", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> downloadDevices(@RequestParam String name) {
		return deviceService.getDeviceFiles(name);
	}

	@RequestMapping(value = "/device/delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteDevice(@RequestParam String name) {
		deviceService.deleteDeviceByName(name);
		return new Response(Status.SUCCESS).map();
	}
}
