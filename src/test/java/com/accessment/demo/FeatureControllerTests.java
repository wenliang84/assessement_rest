package com.accessment.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FeatureControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private MultiValueMap<String, String> map;
	private FeatureRequest request;
	private ObjectMapper objectMapper;

	@BeforeEach
	void init() {
		map = new LinkedMultiValueMap<>();
		map.add("email", "test@email.com");
		map.add("featureName", "test");
		request = new FeatureRequest("test", "test@email.com", true);
		objectMapper = new ObjectMapper();
	}

	@Test
	@Order(1)
	public void testGetFeatureNoRecordReturned() throws Exception {
		this.mockMvc.perform(get("/feature").params(map)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.canAccess").value("false")) ;
	}

	@Test
	@Order(2)
	public void testPostFeatureWithFeatureEnabled() throws Exception {
		this.mockMvc.perform(post("/feature")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	public void testGetFeatureWithRecordReturnedFeatureEnabled() throws Exception {
		this.mockMvc.perform(get("/feature").params(map)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.canAccess").value("true")) ;
	}

	@Test
	@Order(4)
	public void testPostFeatureWithFeatureDisabled() throws Exception {
		request.setEnable(false);
		this.mockMvc.perform(post("/feature")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	public void testGetFeatureWithRecordReturnedFeatureDisabled() throws Exception {
		this.mockMvc.perform(get("/feature").params(map)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.canAccess").value("false")) ;
	}

	@Test
	@Order(6)
	public void testPostFeatureWithError() throws Exception {
		request.setFeatureName(null);
		this.mockMvc.perform(post("/feature")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotModified());
	}

}
