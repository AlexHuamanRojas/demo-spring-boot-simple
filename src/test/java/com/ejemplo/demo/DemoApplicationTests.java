package com.ejemplo.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"demo.kafka.consumer.enabled=false",
		"spring.kafka.admin.auto-create=false"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
