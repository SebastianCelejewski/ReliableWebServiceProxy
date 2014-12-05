package org.sebcel.rwsp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class When_web_service_is_running_at_startup {

	private ApplicationContext context;
	private TestServer server;

	@Before
	public void setup() {
		server = new TestServer(9999, "/wsdl.xml");
		server.setResponse("/getStatusResponse.xml");
		server.setDelay(1);
		server.start();
		context = new ClassPathXmlApplicationContext("test.xml");
	}

	@After
	public void tearDown() {
		server.stop();
	}

	@Test
	public void proxy_client_should_be_instantiated_properly() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		Assert.assertNotNull(testBean);
	}

	@Test
	public void proxy_client_should_handle_request_when_invoked() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		Assert.assertNotNull(testBean.callService());
	}
}