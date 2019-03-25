/**
 * 
 */
package com.turvo.bankingqueue.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.turvo.bankingqueue.constant.ServicePriority;
import com.turvo.bankingqueue.constant.TokenTypeEnum;
import com.turvo.bankingqueue.domain.request.AddressRequest;
import com.turvo.bankingqueue.domain.request.CustomerRequest;
import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.domain.response.TokenResponse;
import com.turvo.bankingqueue.service.TokenService;

/**
 * @author vedantn
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TokenControllerTest extends BaseTest {

	private static final String URI_TOKEN = TokenController.URI_TOKEN;

	private MockMvc mockMvc;

	@Mock
	private TokenService tokenService;

	@InjectMocks
	private TokenController tokenController;

	@Before
	public void setup() {
		this.mockMvc = mockMvcBuilders(TokenControllerTest.class, tokenController);
	}

	@Test
	public void test_createToken_Endpoint() throws Exception {
		
		TokenRequest tokenRequest = defaultTokenRequest();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URI_TOKEN)
				.contentType(MediaType.APPLICATION_JSON).content(toJSON(tokenRequest)).accept(MediaType.APPLICATION_JSON_UTF8);

		mockMvc.perform(builder).andExpect(status().isOk());
	}
	
	
	@Test
	public void test_createToken_success_when_data_provided() throws Exception {

		TokenRequest tokenRequest = defaultTokenRequest();
		TokenResponse tokenResponse = new TokenResponse(tokenRequest);

		when(tokenService.createAndAssignToken(tokenRequest)).thenReturn(tokenResponse);
		ResponseEntity<TokenResponse> responseEntity = tokenController.createToken(tokenRequest);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void test_fetchToken_Endpoint() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URI_TOKEN+"/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8);

		mockMvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	public void test_fetchToken_success() throws Exception {

		TokenResponse tokenResponse = new TokenResponse();
		when(tokenService.fetchToken(1)).thenReturn(tokenResponse);
		ResponseEntity<TokenResponse> responseEntity = tokenController.fetchToken(1);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

}
