package org.sebcel.rwsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class TestServer {

	private Server jetty;
	private String wsdl;
	private String responseBody;
	private Exception exception;
	private int delay;
	private static boolean running;

	public TestServer(int port, String wsdlFilePath) {
		jetty = new Server(port);
		wsdl = loadFile(wsdlFilePath);
		jetty.setStopAtShutdown(true);
		jetty.setHandler(new AbstractHandler() {

			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
				System.out.println("> Received request " + request.getServerName() + ":" + request.getServerPort() + request.getServletPath() + request.getPathInfo() + "?" + request.getQueryString());
				if (!running) {
					throw new RuntimeException("Server is not running!");
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ex) {
					;
				}
				response.setStatus(200);
				response.setContentType("text/xml;charset=utf-8");
				if (request.getQueryString() != null && request.getQueryString().contains("wsdl")) {
					response.getWriter().write(wsdl);
				} else {
					response.getWriter().write(responseBody);
				}
				baseRequest.setHandled(true);
			}
		});
	}

	public void start() {
		try {
			System.out.println("Starting http server");
			running = true;
			jetty.start();
		} catch (Exception ex) {
			throw new RuntimeException("Failed to start http server: " + ex.getMessage(), ex);
		}
	}

	public void stop() {
		try {
			System.out.println("Stopping http server");
			jetty.stop();
			running = false;
		} catch (Exception ex) {
			throw new RuntimeException("Failed to stop http server: " + ex.getMessage(), ex);
		}
	}

	public void setResponse(String responseFilePath) {
		this.responseBody = loadFile(responseFilePath);
	}

	private String loadFile(String filePath) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = this.getClass().getResourceAsStream(filePath);
			byte[] buffer = new byte[1024];
			int readBytes = 0;
			do {
				readBytes = in.read(buffer);
				if (readBytes > 0) {
					out.write(buffer, 0, readBytes);
				}
			} while (readBytes > 0);
			in.close();
			out.flush();
			out.close();

			return new String(out.toByteArray());
		} catch (Exception ex) {
			throw new RuntimeException("Failed to get contents of file " + filePath + ": " + ex.getMessage(), ex);
		}
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setException(Exception exeption) {
		this.exception = exception;
	}
}