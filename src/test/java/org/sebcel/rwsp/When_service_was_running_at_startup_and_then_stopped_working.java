package org.sebcel.rwsp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class When_service_was_running_at_startup_and_then_stopped_working {

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

	@Test(expected = WebServiceInvocationException.class)
	public void proxy_client_should_throw_exception_when_invoked() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		server.stop();
		Assert.assertNotNull(testBean.callService());
	}
}