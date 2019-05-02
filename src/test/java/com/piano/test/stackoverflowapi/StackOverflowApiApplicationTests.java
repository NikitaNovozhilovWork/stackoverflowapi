package com.piano.test.stackoverflowapi;

import com.piano.test.stackoverflowapi.gateways.StackOverflowGateway;
import com.piano.test.stackoverflowapi.models.api.SearchRequest;
import com.piano.test.stackoverflowapi.models.gateway.StackOverflowSearchResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StackOverflowApiApplication.class)
public class StackOverflowApiApplicationTests {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private StackOverflowGateway stackOverflowGateway;

	private MockMvc mockMvc;

	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.build();
	}

	@Test
	public void stackOverflowSearchExample() throws Exception {
		//given
		when(stackOverflowGateway.search(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(
				new StackOverflowSearchResponse()
						.setHasMore(true)
						.setItems(Arrays.asList(
								new StackOverflowSearchResponse.Item()
										.setQuestionId(0)
										.setTitle("Title")
										.setCreationDate(1556733623)
										.setAnswered(false)
										.setLink("http://link.com/title_1/link")
										.setOwner(new StackOverflowSearchResponse.Owner()
												.setDisplayName("FirstTest QuestionOwner")),
								new StackOverflowSearchResponse.Item()
										.setQuestionId(1)
										.setTitle("Title 2")
										.setCreationDate(1556733624)
										.setAnswered(true)
										.setLink("http://link.com/title_2/link")
										.setOwner(new StackOverflowSearchResponse.Owner()
												.setDisplayName("SecondTest QuestionOwner")))));
		ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(SearchRequest.class);

		//when
		this.mockMvc.perform(post("/api/v1/search")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"title\":\"Title\"," +
						"\"page\":1," +
						"\"size\":2," +
						"\"order\":\"desc\"," +
						"\"sort\":\"activity\"," +
						"\"site\":\"stackoverflow\"}"))
				//then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.hasMore", is(true)))
				.andExpect(jsonPath("$.questions[0].id", is(0)))
				.andExpect(jsonPath("$.questions[0].ownerName", is("FirstTest QuestionOwner")))
				.andExpect(jsonPath("$.questions[0].title", is("Title")))
				.andExpect(jsonPath("$.questions[0].link", is("http://link.com/title_1/link")))
				.andExpect(jsonPath("$.questions[0].creationDate", is(1556733623)))
				.andExpect(jsonPath("$.questions[0].answered", is(false)))
				.andExpect(jsonPath("$.questions[1].id", is(1)))
				.andExpect(jsonPath("$.questions[1].ownerName", is("SecondTest QuestionOwner")))
				.andExpect(jsonPath("$.questions[1].title", is("Title 2")))
				.andExpect(jsonPath("$.questions[1].link", is("http://link.com/title_2/link")))
				.andExpect(jsonPath("$.questions[1].creationDate", is(1556733624)))
				.andExpect(jsonPath("$.questions[1].answered", is(true)))
				.andDo(document("{method-name}",
					requestFields(
							fieldWithPath("title").description("Words in title for searching")
									.attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("title"))),
							fieldWithPath("page").description("Page for pagination").optional()
									.attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("page"))),
							fieldWithPath("size").description("Size of results per page")
									.attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("size"))),
							fieldWithPath("order").description("Sort ordering")
									.attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("order"))),
							fieldWithPath("sort").description("Sort column")
									.attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("sort"))),
							fieldWithPath("site").description("StackExchange site for searching")
									.attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("site")))),
					responseFields(
							fieldWithPath("hasMore").description("Is next page available for loading"),
							fieldWithPath("questions[].id").description("Question unique identifier"),
							fieldWithPath("questions[].ownerName").description("Question owner name").optional(),
							fieldWithPath("questions[].title").description("Original question title"),
							fieldWithPath("questions[].link").description("link to the original question"),
							fieldWithPath("questions[].creationDate").description("Question creation date"),
							fieldWithPath("questions[].answered").description("Is question answered"))));
	}

}
