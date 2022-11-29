package weg.net.tester;

import org.jdesktop.application.SingleFrameApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gateway.DesktopApplicationGateway;

@SpringBootApplication
public class TesterApplication {

	public static void main(String[] args) {
		SingleFrameApplication.launch(DesktopApplicationGateway.class, args);
		SpringApplication.run(TesterApplication.class, args);
	}

}
