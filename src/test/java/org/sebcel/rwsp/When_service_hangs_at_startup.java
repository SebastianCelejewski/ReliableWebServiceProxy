package org.sebcel.rwsp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class When_service_hangs_at_startup {

	private ApplicationContext context;
	private TestServer server;

	@Before
	public void setup() {
		server = new TestServer(9999, "/wsdl.xml");
		server.setDelay(5000);
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

	@Test(expected = WebServiceInstantiationException.class)
	public void proxy_client_should_time_out_when_invoked() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		testBean.callService();
	}
}