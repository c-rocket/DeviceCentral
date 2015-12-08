package com.oracle.iot.devicecentral.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
	private String status;
	private String message;

	public Response(Status status) {
		this.status = status.getHtmlStatus();
		this.message = status.getMessage();
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, Object> map() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", status);
		map.put("message", message);
		return map;
	}
}
