package net.youssfi.bankaccountservice.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.youssfi.bankaccountservice.dto.request.*;
import net.youssfi.bankaccountservice.dto.response.AccountDetailsDTO;
import net.youssfi.bankaccountservice.dto.response.BankAccountDTO;
import net.youssfi.bankaccountservice.dto.response.GetBankStatsResponseDTO;
import net.youssfi.bankaccountservice.dto.response.TransferResponseDTO;
import net.youssfi.bankaccountservice.enums.AccountStatus;
import net.youssfi.bankaccountservice.enums.AccountType;
import net.youssfi.bankaccountservice.exception.BankAccountNotFoundException;
import net.youssfi.bankaccountservice.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name ="Bearer Authentication")
public class BankAccountRestController {
    private BankAccountService bankAccountService;


    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('USER')")
    public List<BankAccountDTO> allAccounts(){
        return bankAccountService.getAllBankAccounts();
    }
    @GetMapping("/accountsByType")
    @PreAuthorize("hasAuthority('USER')")
    public List<BankAccountDTO> accountsByType(@RequestParam(name="type") AccountType type){
        return bankAccountService.getAccountsByType(type);
    }
    @GetMapping("/accountsByStatus")
    @PreAuthorize("hasAuthority('USER')")
    public List<BankAccountDTO> accountsByStatus(@RequestParam(name="status") AccountStatus status){
        return bankAccountService.getAccountsByStatus(status);
    }
    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> accountById(@PathVariable String accountId){
        try {
            BankAccountDTO account = bankAccountService.getBankAccountById(accountId);
            return ResponseEntity.ok().body(account);
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }
    @PostMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveNewAccount(@RequestBody SaveNewAccountRequestDTO request){
        try {
            BankAccountDTO account = bankAccountService.saveNewAccount(request);
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }
    @PutMapping ("/accounts/debit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> debitAccount(@RequestBody DebitAccountRequestDTO request){
        try {
            BankAccountDTO account = bankAccountService.debitAccount(request);
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }
    @PutMapping ("/accounts/credit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> creditAccount(@RequestBody CreditAccountRequestDTO request){
        try {
            BankAccountDTO account = bankAccountService.creditAccount(request);
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }
    @PutMapping ("/accounts/transfer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> transfer(@RequestBody TransferRequestDTO request){
        try {
            TransferResponseDTO transfer = bankAccountService.transfer(request);
            return ResponseEntity.ok().body(transfer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }
    @PutMapping ("/accounts/changeStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeAccountStatusRequestDTO request){
        try {
            BankAccountDTO bankAccountDTO = bankAccountService.changeStatusTo(request);
            return ResponseEntity.ok().body(bankAccountDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }
    @GetMapping("/accounts/stats")
    @PreAuthorize("hasAuthority('USER')")
    public GetBankStatsResponseDTO getTotalBalance(){
        return bankAccountService.getBankStats();
    }

    @GetMapping("/accountDetails/{accountId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> accountDetails(@PathVariable String accountId){
        try {
            AccountDetailsDTO accountDetailsDTO = bankAccountService.accountDetails(accountId);
            return ResponseEntity.ok().body(accountDetailsDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message",e.getMessage()));
        }
    }

}
