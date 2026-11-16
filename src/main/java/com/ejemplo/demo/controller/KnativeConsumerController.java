
package com.ejemplo.demo.controller;

import com.ejemplo.demo.service.KnativeConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KnativeConsumerController {

	private final KnativeConsumerService knativeConsumerService;

	@PostMapping("/")
	public ResponseEntity<Void> recibir(
			@RequestBody String payload,
			@RequestHeader org.springframework.util.MultiValueMap<String, String> headers) {
		log.info(">>> ENDPOINT RAIZ / <<<");
		log.info("====================================");
		log.info("Petición recibida desde Knative");

		log.info("========== HEADERS ==========");
		headers.forEach((key, value) ->
				log.info("{} = {}", key, value));

		log.info("========== VALUE ==========");
		knativeConsumerService.ejecutar(payload);

		log.info("====================================");

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/clientes")
	public ResponseEntity<Void> recibir2(
			@RequestBody String payload,
			@RequestHeader org.springframework.util.MultiValueMap<String, String> headers) {
		log.info(">>> ENDPOINT API CLIENTES <<<");
		log.info("====================================");
		log.info("Petición recibida desde Knative");

		log.info("========== HEADERS ==========");
		headers.forEach((key, value) ->
				log.info("{} = {}", key, value));

		log.info("========== VALUE ==========");
		knativeConsumerService.ejecutar(payload);

		log.info("====================================");

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/ext/clientes")
	public ResponseEntity<Void> recibir3(
			@RequestBody String payload,
			@RequestHeader MultiValueMap<String, String> headers) {
		log.info(">>> ENDPOINT API EXT <<<");
		log.info("========== EVENTO RECIBIDO ==========");

		headers.forEach((key, value) ->
				log.info("{} = {}", key, value));

		log.info("PAYLOAD: {}", payload);

		log.info("Evento procesado correctamente");

		return ResponseEntity.ok().build();
	}
}
