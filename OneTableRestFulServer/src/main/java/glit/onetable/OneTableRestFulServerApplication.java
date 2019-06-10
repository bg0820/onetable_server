package glit.onetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import glit.onetable.model.HangleAnalyze;


@SpringBootApplication
public class OneTableRestFulServerApplication {

	public static void main(String[] args) {
		HangleAnalyze.getInstance().initilze();
		SpringApplication.run(OneTableRestFulServerApplication.class, args);
	}

}
