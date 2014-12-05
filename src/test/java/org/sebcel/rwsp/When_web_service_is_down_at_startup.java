package org.sebcel.rwsp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class When_web_service_is_down_at_startup {

	private ApplicationContext context;
	private TestServer server;

	@Before
	public void setup() {
		server = new TestServer(9999, "/wsdl.xml");
		server.setDelay(0);
		context = new ClassPathXmlApplicationContext("test.xml");
	}

	@Test
	public void proxy_client_should_be_instantiated_properly() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		Assert.assertNotNull(testBean);
	}

	@Test(expected = WebServiceInstantiationException.class)
	public void proxy_client_should_throw_exception_when_invoked() {
		TestBean testBean = (TestBean) context.getBean("testBean");
		testBean.callService();
	}
}