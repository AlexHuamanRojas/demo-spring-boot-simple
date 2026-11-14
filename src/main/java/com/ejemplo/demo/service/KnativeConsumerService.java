
package com.ejemplo.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KnativeConsumerService {

	public void ejecutar(String payload) {

		log.info("====================================");
		log.info("KNATIVE DESPERTÓ");
		log.info("Mensaje recibido:");
		log.info("{}", payload);
		log.info("====================================");
	}
}
