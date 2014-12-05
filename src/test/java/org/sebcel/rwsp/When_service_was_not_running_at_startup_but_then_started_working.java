package org.sebcel.rwsp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class When_service_was_not_running_at_startup_but_then_started_working {

	private ApplicationContext context;
	private TestServer server;

	@Before
	public void setup() {
		server = new TestServer(9999, "/wsdl.xml");
		server.setResponse("/getStatusResponse.xml");
		server.setDelay(1);
		context = new ClassPathXmlApplicationContext("test.xml");
	}

	@After
	public void tearDown() {
		server.stop();
	}

	@Test
	public void proxy_client_should_throw_exception_when_invoked() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		server.start();
		Assert.assertNotNull(testBean.callService());
	}
}