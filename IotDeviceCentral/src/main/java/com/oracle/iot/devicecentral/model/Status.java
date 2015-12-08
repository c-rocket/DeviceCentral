package com.oracle.iot.devicecentral.model;

public enum Status {
	SUCCESS("200", "success"), CREATED("201", "created"), BAD_PARAMS("400", "Bad Parameters"), SAVE_USAGE("200",
			"You can upload a properties file by posting to this same URL.");
	String htmlStatus;
	String message;

	Status(String htmlStatus, String message) {
		this.htmlStatus = htmlStatus;
		this.message = message;
	}

	public String getHtmlStatus() {
		return htmlStatus;
	}

	public String getMessage() {
		return message;
	}

}
