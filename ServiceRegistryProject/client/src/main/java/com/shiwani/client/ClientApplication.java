package com.shiwani.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ClientApplication {

	@Autowired
	private EurekaClient client;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@RequestMapping("/v1")
	public String callService()
	{

		RestTemplate restTemplate = restTemplateBuilder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka("service1",false);
		String baseUrl = instanceInfo.getHomePageUrl();
		System.out.println("Hi this url is :"+baseUrl);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/v2/", HttpMethod.GET,null,String.class);
		return response.getBody();
	}
	@RequestMapping("/test/v1")
	public String callTestV1()
	{
		return "Test called for v1";
	}

	@RequestMapping("/test/v2")
	public String callTestV2()
	{
		return "Test called for v2";
	}

}
