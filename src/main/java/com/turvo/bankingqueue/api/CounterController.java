package com.turvo.bankingqueue.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.turvo.bankingqueue.helper.CounterDetails;
import com.turvo.bankingqueue.service.CounterService;

/**
 * @author vedantn
 *
 */

@RestController
public class CounterController {

	private static final String PATH_PARAM_TOKEN_ID = "/{branchName}";
	static final String URI_COUNTER = "/counter/status";
	static final String BASE_URI = PATH_PARAM_TOKEN_ID + URI_COUNTER;
	
	@Autowired
	private CounterService counterService;

	/**
	 *
	 * returns available counters details along with current counter queue ticketId list
	 *
	 * @return List of CounterDetails
	 */
	@GetMapping(URI_COUNTER)	
	public ResponseEntity<List<CounterDetails>> fetchAvailableCounters() {
		return new ResponseEntity<List<CounterDetails>>(counterService.getCounterStatus(), HttpStatus.OK);
	}

	/**
	 *
	 * returns available counters details along with current counter queue ticketId list
	 *
	 * @return List of CounterDetails
	 */
	@GetMapping(BASE_URI)
	public ResponseEntity<List<CounterDetails>> fetchCounterStatus(@PathVariable String branchName) {
		return new ResponseEntity<List<CounterDetails>>(counterService.getCounterStatus(branchName),HttpStatus.OK);
	}

	

}
