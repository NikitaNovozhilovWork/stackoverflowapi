package com.piano.test.stackoverflowapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StackOverflowApiApplication.class)
public class StackOverflowApiApplicationValidationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.build();
	}

	@Test
	public void stackOverflowSearchTitleValidation() throws Exception {
		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"\"," +
						"\"page\":1," +
						"\"size\":2," +
						"\"order\":\"desc\"," +
						"\"sort\":\"activity\"," +
						"\"site\":\"stackoverflow\"}"))
				//then
				.andExpect(status().isBadRequest());
	}

	@Test
	public void stackOverflowSearchPageValidation() throws Exception {
		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"Title\"," +
						"\"page\":0," +
						"\"size\":2," +
						"\"order\":\"desc\"," +
						"\"sort\":\"activity\"," +
						"\"site\":\"stackoverflow\"}"))
				//then
				.andExpect(status().isBadRequest());
	}

	@Test
	public void stackOverflowSearchSizeValidation() throws Exception {
		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"Title\"," +
						"\"page\":1," +
						"\"size\":0," +
						"\"order\":\"desc\"," +
						"\"sort\":\"activity\"," +
						"\"site\":\"stackoverflow\"}"))
				//then
				.andExpect(status().isBadRequest());
	}

	@Test
	public void stackOverflowSearchOrderValidation() throws Exception {
		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"Title\"," +
						"\"page\":1," +
						"\"size\":2," +
						"\"sort\":\"activity\"," +
						"\"site\":\"stackoverflow\"}"))
				//then
				.andExpect(status().isBadRequest());
	}

	@Test
	public void stackOverflowSearchSortValidation() throws Exception {
		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"Title\"," +
						"\"page\":1," +
						"\"size\":2," +
						"\"order\":\"desc\"," +
						"\"site\":\"stackoverflow\"}"))
				//then
				.andExpect(status().isBadRequest());
	}

	@Test
	public void stackOverflowSearchSiteValidation() throws Exception {
		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"Title\"," +
						"\"page\":1," +
						"\"size\":2," +
						"\"order\":\"desc\"," +
						"\"sort\":\"activity\"}"))
				//then
				.andExpect(status().isBadRequest());
	}

}
