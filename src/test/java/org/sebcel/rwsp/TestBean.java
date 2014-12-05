package org.sebcel.rwsp;

import org.sebcel.webservice.GetStatusRequest;
import org.sebcel.webservice.GetStatusResponse;
import org.sebcel.webservice.TestService;

public class TestBean {

	private TestService testService;

	public TestBean(TestService testService) {
		this.testService = testService;
	}

	public TestBean() {
		System.out.println("TestBean has been instantiated");
	}

	public int callService() {
		GetStatusRequest getStatusRequest = new GetStatusRequest();
		GetStatusResponse response = testService.getStatus(getStatusRequest);
		return response.getErrorCode();
	}
}