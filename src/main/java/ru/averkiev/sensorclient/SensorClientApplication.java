package ru.averkiev.sensorclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class SensorClientApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SensorClientApplication.class, args);
		final String sensorName = "SensorTest1";

		registerSensor(sensorName);

		Random random = new Random();

		double maxTemperature = 20.0;
		for (int i = 0; i < 500; i ++) {
			System.out.println(i);
			sendMeasurement(random.nextDouble() * maxTemperature, random.nextBoolean(), sensorName);
		}
	}

	private static void registerSensor(String sensorName) {
		final String url = "http://localhost:8080/sensors/registration";

		Map<String, Object> jsonData = new HashMap<>();
		jsonData.put("name", sensorName);

		makePostRequestWithJSONData(url, jsonData);
	}

	private static void sendMeasurement(double value, boolean isRainy, String sensorName) {
		final String url = "http://localhost:8080/measurements/add";

		Map<String, Object> jsonData = new HashMap<>();
		jsonData.put("value", value);
		jsonData.put("isRaining", isRainy);
		jsonData.put("sensor", Map.of("name", sensorName));

		makePostRequestWithJSONData(url, jsonData);
	}

	private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

		try {
			restTemplate.postForObject(url, request, String.class);
			System.out.println("Измерение успешно отправлено на сервер!");
		} catch (HttpServerErrorException e) {
			System.out.println("ОШИБКА!");
			System.out.println(e.getMessage());
		}
	}

}
