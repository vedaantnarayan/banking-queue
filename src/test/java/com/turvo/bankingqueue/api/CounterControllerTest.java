/**
 * 
 */
package com.turvo.bankingqueue.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.turvo.bankingqueue.helper.CounterDetails;
import com.turvo.bankingqueue.service.CounterService ;

/**
 * @author vedantn
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CounterControllerTest extends BaseTest {

	private static final String URI_COUNTER = CounterController.URI_COUNTER;

	private MockMvc mockMvc;

	@Mock
	private CounterService counterService;

	@InjectMocks
	private CounterController counterController;

	@Before
	public void setup() {
		this.mockMvc = mockMvcBuilders(CounterControllerTest.class, counterController);
	}

	@Test
	public void test_fetchAvailableCounters_Endpoint() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URI_COUNTER)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);

		mockMvc.perform(builder).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_fetchAvailableCounters_success() throws Exception {

		List<CounterDetails> counterDetailsList = new ArrayList<CounterDetails>();
		when(counterService.getCounterStatus()).thenReturn(counterDetailsList);
		ResponseEntity<List<CounterDetails>> responseEntity = counterController.fetchAvailableCounters();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	
	@Test
	public void test_fetchCounterStatus_Endpoint() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/midtown"+URI_COUNTER)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);

		mockMvc.perform(builder).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_fetchCounterStatus_success() throws Exception {
		String branchName ="midtown";
		List<CounterDetails> counterDetailsList = new ArrayList<CounterDetails>();
		when(counterService.getCounterStatus(branchName)).thenReturn(counterDetailsList);
		ResponseEntity<List<CounterDetails>> responseEntity = counterController.fetchCounterStatus(branchName);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
}
