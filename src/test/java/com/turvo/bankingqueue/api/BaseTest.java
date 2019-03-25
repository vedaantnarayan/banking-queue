/**
 * 
 */
package com.turvo.bankingqueue.api;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turvo.bankingqueue.constant.ServicePriority;
import com.turvo.bankingqueue.constant.TokenTypeEnum;
import com.turvo.bankingqueue.domain.request.AddressRequest;
import com.turvo.bankingqueue.domain.request.CustomerRequest;
import com.turvo.bankingqueue.domain.request.TokenRequest;

public class BaseTest {

	public static MockMvc mockMvcBuilders(Class<?> testClass, Object... controllers) {
		initializeMocks(testClass);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
		return mockMvc;
	}

	public static void initializeMocks(Class<?> testClass) {
		MockitoAnnotations.initMocks(testClass);
	}

	public static String toJSON(Object obj) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(obj);
		return content;
	}
	
	public static TokenRequest defaultTokenRequest() {
		
		TokenRequest tokenRequest = new TokenRequest();
		
		tokenRequest.setTokenId(1);
		tokenRequest.setBranchName("midtwon");
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setCustomerId(1);
		customerRequest.setName("Terry");
		customerRequest.setPhoneNumber("90998778676");
		AddressRequest addressRequest = new AddressRequest();
		addressRequest.setCity("Hyderabad");
		addressRequest.setCountry("India");
		addressRequest.setState("Telengana");
		addressRequest.setZipcode("5600087");
		customerRequest.setAddress(addressRequest);
		tokenRequest.setCustomer(customerRequest);
		tokenRequest.setServicePriority(ServicePriority.valueOf("REGULAR"));
		tokenRequest.setTokenType(TokenTypeEnum.valueOf("ACCOUNT_OPEN"));
		return tokenRequest;
	}
}
