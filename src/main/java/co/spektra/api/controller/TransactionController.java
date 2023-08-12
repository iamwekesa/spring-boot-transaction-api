package co.spektra.api.controller;


import co.spektra.api.ApiResponse;
import co.spektra.api.model.Transaction;
import co.spektra.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactions() {
        List<Transaction> transactionListData = transactionService.getAllTransactions();
        ApiResponse<List<Transaction>> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transactions retrieved successfully", transactionListData);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> getTransactionById(@PathVariable Long id) {
        Transaction transactionData = transactionService.getTransactionById(id);
        ApiResponse<Transaction> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction retrieved successfully", transactionData);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody Transaction transaction) {

        Transaction createdTransactionData = transactionService.createTransaction(transaction);
        ApiResponse<Transaction> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction Created successfully", createdTransactionData);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction updatedTransactionData = transactionService.updateTransaction(id, transaction);

        ApiResponse<Transaction> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction Updated successfully", updatedTransactionData);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        ApiResponse<Void> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction Deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);

    }
}
