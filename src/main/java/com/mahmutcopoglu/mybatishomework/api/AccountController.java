package com.mahmutcopoglu.mybatishomework.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*import org.springframework.kafka.core.KafkaTemplate;*/
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mahmutcopoglu.mybatishomework.dto.AccountAmountRequest;
import com.mahmutcopoglu.mybatishomework.dto.AccountCreateRequest;
import com.mahmutcopoglu.mybatishomework.dto.MoneyTransferRequest;
import com.mahmutcopoglu.mybatishomework.entity.Account;
import com.mahmutcopoglu.mybatishomework.entity.Log;
import com.mahmutcopoglu.mybatishomework.repository.LogRepository;
import com.mahmutcopoglu.mybatishomework.responses.AccountCreateInvalidTypeResponse;
import com.mahmutcopoglu.mybatishomework.responses.AccountCreateSuccessResponse;
import com.mahmutcopoglu.mybatishomework.responses.AccountLogResponse;
import com.mahmutcopoglu.mybatishomework.responses.ForbiddenResponse;
import com.mahmutcopoglu.mybatishomework.service.AccountService;


@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private LogRepository myBatisLogRepository;
	
	/*
	 * @Autowired private KafkaTemplate<String,String> producer;
	 */
	
	@PostMapping("/accounts")
	public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequest accountCreateRequest){
		
		Account account = this.accountService.save(
				accountCreateRequest.getName(),
				accountCreateRequest.getSurname(),
				accountCreateRequest.getEmail(),
				accountCreateRequest.getTc(),
				accountCreateRequest.getType());
		
		if(account == null) {
			AccountCreateInvalidTypeResponse response = new AccountCreateInvalidTypeResponse();
			response.setMessage("Invalid Account Type:" + accountCreateRequest.getType());
			return ResponseEntity.badRequest().body(response);
		}
		else {
			AccountCreateSuccessResponse response = new AccountCreateSuccessResponse();
			response.setAccountNumber(account.getAccountNumber());
			response.setMessage("Account Created");
			return ResponseEntity.ok().body(response);
		}
	}
	
	@GetMapping("/accounts/{id}")
	public ResponseEntity<?> detail(@PathVariable int id) {
		if(this.accountService.hasAuth(id)) {
			Account account = this.accountService.findById(id);
			return ResponseEntity.ok().lastModified(account.getLastModified()).body(account);
		}else {
			ForbiddenResponse forbiddenResponse = new ForbiddenResponse();
			forbiddenResponse.setMessage("Invalid Account Id");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);
		}
		
	}
	
	@PatchMapping(path = "/account/{id}")
	public ResponseEntity<?> deposit(@PathVariable(name = "id") int id,
									  @RequestBody AccountAmountRequest accountAmountRequest) {
		if(this.accountService.hasAuth(id)) {
		Account account = this.accountService.updateBalance(id, accountAmountRequest.getAmount());
		String message = account.getAccountNumber() + " deposit amount:" + accountAmountRequest.getAmount() + " " + account.getType();
		/* producer.send("log",message); */
		return ResponseEntity.ok().body(account);
		}else {
			ForbiddenResponse forbiddenResponse = new ForbiddenResponse();
			forbiddenResponse.setMessage("Invalid Account Id");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);
		}
		
	}
	
	@PostMapping("/accounts/{id}")
	public ResponseEntity<?> transfer(@PathVariable(name = "id") int id,
			  @RequestBody MoneyTransferRequest moneyTransferRequest){
		if(this.accountService.hasAuth(id)) {
		Account account = this.accountService.findById(id);
		Account transferAccount = this.accountService.findById(moneyTransferRequest.getTransferredAccountId());
		boolean result = this.accountService.transfer(moneyTransferRequest.getAmount(), id, moneyTransferRequest.getTransferredAccountId());
		if(result) {
			String message = account.getAccountNumber() + " transfer amount:" + moneyTransferRequest.getAmount()+ " " + account.getType() + ",transferred_account:" + transferAccount.getAccountNumber();
			/* producer.send("log",message); */
			return ResponseEntity.ok().body("Transfer Successfull");
		}
		return ResponseEntity.badRequest().body("Insufficient Balance");
		}else {
			ForbiddenResponse forbiddenResponse = new ForbiddenResponse();
			forbiddenResponse.setMessage("Invalid Account Id");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);
		}
	}
	
	@GetMapping(path = "/account/log/{accountNumber}")
	private ResponseEntity<List<?>> getAccountLogs(@PathVariable(name = "accountNumber") long accountNumber){
		List<Log> logs = this.myBatisLogRepository.findByAccountNumber(accountNumber);
		List<AccountLogResponse> accountLogResponse = new ArrayList<AccountLogResponse>();
		for(Log accountLog : logs) {
			accountLogResponse.add(AccountLogResponse.builder().log(accountLog.getMessage()).build());
		}
		return ResponseEntity.ok().body(accountLogResponse);
	}
	
	@DeleteMapping(path = "/account/{id}")
	private ResponseEntity<?> deleteAccount(@PathVariable(name = "id") int id){
		boolean deleteRecord = this.accountService.delete(id);
		if(deleteRecord)
		{
			return ResponseEntity.ok().body(true);
		}else {
			return ResponseEntity.ok().body(false);
		}
		
	}
	
}
