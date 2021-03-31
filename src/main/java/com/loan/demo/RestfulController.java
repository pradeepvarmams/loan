package com.loan.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulController {

	List<Loan> loandetails = new ArrayList<Loan>();

	HashMap<String, Integer> fees = new HashMap<String, Integer>() {
		{
			put("student", 0);
			put("auto", 500);
			put("personal", 750);
			put("mortgage", 1500);
		}
	};

	@PostMapping("/newloan")
	public ResponseEntity addLoan(@RequestBody Loan loan) {
		createAPR(loan);
		return ResponseEntity.ok("success");
	}

	private void createAPR(Loan loan) {

		BigDecimal interest = BigDecimal.valueOf(loan.getPrincipal()).multiply(BigDecimal.valueOf(loan.getRate()))
				.multiply(BigDecimal.valueOf(loan.getTerm())).divide(BigDecimal.valueOf(100));
		long fs = fees.get(loan.getType()).longValue();
		BigDecimal top = interest.add(BigDecimal.valueOf(fs)).divide(BigDecimal.valueOf(loan.getPrincipal()));
		BigDecimal apr = top.divide(BigDecimal.valueOf(loan.getTerm())).multiply(BigDecimal.valueOf(365))
				.multiply(BigDecimal.valueOf(365));
		loan.setApr(apr);

		loandetails.add(loan);
	}

	@GetMapping("/retrieveLoan/{name}")
	public Loan retrieveLoan(@PathVariable String name) {
		return loandetails.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
	}

}
