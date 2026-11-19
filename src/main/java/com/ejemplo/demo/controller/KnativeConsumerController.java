
package com.ejemplo.demo.controller;

import com.ejemplo.demo.service.KnativeConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;

import org.springframework.integration.cloudevents.transformer.FromCloudEventTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KnativeConsumerController {

	private final KnativeConsumerService knativeConsumerService;

	private final FromCloudEventTransformer fromCloudEventTransformer;

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

	@PostMapping("/api/ext/clientes2")
	public ResponseEntity<Void> recibir4(
			@RequestBody String payload,
			@RequestHeader MultiValueMap<String, String> headers) {

		log.info(">>> ENDPOINT API EXT2 <<<");
		log.info("========== CLOUD EVENT RECIBIDO ==========");

		// 1. Obtener atributos CloudEvent del HTTP
		String eventId = headers.getFirst("ce-id");
		String eventSource = headers.getFirst("ce-source");
		String eventType = headers.getFirst("ce-type");

		if (eventId == null || eventSource == null || eventType == null) {
			log.warn("Faltan atributos obligatorios de CloudEvents");
			return ResponseEntity.badRequest().build();
		}

		// 2. Construir el objeto CloudEvent
		CloudEvent cloudEvent = CloudEventBuilder.v1()
				.withId(eventId)
				.withSource(URI.create(eventSource))
				.withType(eventType)
				.withData(
						"application/json",
						payload.getBytes(StandardCharsets.UTF_8)
				)
				.build();

		// 3. Transformar CloudEvent a mensaje Spring Integration
		Message<?> message = fromCloudEventTransformer.transform(
				MessageBuilder.withPayload(cloudEvent).build()
		);

		// 4. Recuperar los datos transformados
		String data = new String(
				(byte[]) message.getPayload(),
				StandardCharsets.UTF_8
		);

		// 5. Mostrar resultado
		log.info("========== SPRING INTEGRATION ==========");
		log.info("CloudEvent ID: {}", message.getHeaders().get("ce-id"));
		log.info("CloudEvent Source: {}", message.getHeaders().get("ce-source"));
		log.info("CloudEvent Type: {}", message.getHeaders().get("ce-type"));
		log.info("CloudEvent Data: {}", data);

		log.info("========== HEADERS DEL MENSAJE ==========");
		message.getHeaders().forEach((key, value) ->
				log.info("{} = {}", key, value));

		log.info("Evento procesado correctamente");

		return ResponseEntity.ok().build();
	}
}
