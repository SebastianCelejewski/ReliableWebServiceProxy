package org.sebcel.rwsp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.spi.ServiceDelegate;

public class ReliableServiceProxyFactory<T> implements InvocationHandler {

	private ReliableServiceProxyParameters parameters;

	private Object target;
	private Class<T> targetClass;
	private RuntimeException ex;

	@SuppressWarnings("unchecked")
	public Object create(ReliableServiceProxyParameters parameters) {
		this.parameters = parameters;
		try {
			System.out.println("Creating proxy for " + parameters.getServiceName() + " at " + parameters.getServiceURL());
			targetClass = (Class<T>) Class.forName(parameters.getInterfaceName());

			Class proxyClass = Proxy.getProxyClass(targetClass.getClassLoader(), new Class[] { targetClass });
			Object serviceProxy = proxyClass.getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { this });
			System.out.println("Successfully created proxy for " + parameters.getInterfaceName() + ".");

			ping();

			return serviceProxy;
		} catch (Exception ex) {
			throw new RuntimeException("Failed to create proxy for " + parameters.getInterfaceName() + ": " + ex.getMessage(), ex);
		}
	}

	private void ping() {
		System.out.println("Checking web service availability for " + parameters.getServiceURL());
		try {
			synchronized (this) {
				target = createTarget();
				if (target != null) {
					System.out.println("Web service " + parameters.getServiceURL() + " is available");
				}
			}
		} catch (Exception ex) {
			System.err.println("Web service " + parameters.getServiceURL() + " is not available.");
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		synchronized (this) {
			if (target == null) {
				target = createTarget();
			}
		}

		return invoke(method, args);
	}

	private Object invoke(Method method, Object[] args) throws Exception {
		Method targetMethod = getTargetMethod(method);
		try {
			return targetMethod.invoke(target, args);
		} catch (Exception ex) {
			throw new WebServiceInvocationException("Failed to invoke method " + method + ": " + ex.getMessage(), ex);
		}
	}

	private Method getTargetMethod(Method method) throws NoSuchMethodException {
		Class<?> targetClass = target.getClass();
		for (Method m : targetClass.getDeclaredMethods()) {
			if (m.getName().equals(method.getName())) {
				return m;
			}
		}
		throw new RuntimeException("Failed to find method " + method + " in class " + target.getClass());
	}

	private Object createTarget() {
		System.out.println("Creating web service client for " + parameters.getServiceURL());

		ex = null;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					URL wsdlUrl = new URL(parameters.getServiceURL() + "?wsdl");
					QName serviceQName = new QName(parameters.getTargetNamespace(), parameters.getServiceName());
					ServiceDelegate serviceDelegate = Provider.provider().createServiceDelegate(wsdlUrl, serviceQName, this.getClass());
					QName portQName = new QName(parameters.getTargetNamespace(), parameters.getPortName());
					Object port = serviceDelegate.getPort(portQName, targetClass);
					System.out.println("Web service client for " + parameters.getServiceURL() + " has been created successfully.");
					target = port;
				} catch (Exception e) {
					ex = new WebServiceInstantiationException("Failed to create web service client for " + parameters.getServiceURL() + ": " + e.getMessage(), e);
				}
			}
		});

		t.start();

		try {
			Thread.sleep(parameters.getTimeout());
		} catch (InterruptedException e) {
			;
		}

		if (ex != null) {
			throw ex;
		}

		if (target == null) {
			throw new WebServiceInstantiationException("Failed to create proxy to " + parameters.getServiceURL() + ": timeout.");
		}

		System.out.println("Web service proxy has been created successfully");
		return target;
	}
}