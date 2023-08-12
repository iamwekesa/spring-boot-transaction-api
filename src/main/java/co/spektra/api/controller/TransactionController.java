package co.spektra.api.controller;


import co.spektra.api.ApiResponse;
import co.spektra.api.exception.InvalidTransactionException;
import co.spektra.api.exception.TransactionNotFoundException;
import co.spektra.api.model.Transaction;
import co.spektra.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * Retrieve a list of all transactions.
     *
     * @param page The page number for pagination (optional).
     * @param size The number of transactions per page (optional).
     * @return A list of transactions.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<Transaction>>> getAllTransactions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<Transaction> transactionListData = transactionService.getAllTransactions(page, size);

        ApiResponse<Page<Transaction>> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transactions retrieved successfully", transactionListData);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    /**
     * Retrieve a specific transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return The transaction with the specified ID.
     * @throws TransactionNotFoundException if the transaction does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> getTransactionById(@PathVariable Long id) {
        Transaction transactionData = transactionService.getTransactionById(id);
        ApiResponse<Transaction> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction retrieved successfully", transactionData);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    /**
     * Create a new transaction.
     *
     * @param transaction The transaction data to create.
     * @return The newly created transaction.
     * @throws InvalidTransactionException if the input data is invalid.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody Transaction transaction) {

        Transaction createdTransactionData = transactionService.createTransaction(transaction);
        ApiResponse<Transaction> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction Created successfully", createdTransactionData);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
    /**
     * Update an existing transaction.
     *
     * @param id The ID of the transaction to update.
     * @param transaction The updated transaction data.
     * @return The updated transaction.
     * @throws TransactionNotFoundException if the transaction does not exist.
     * @throws InvalidTransactionException if the input data is invalid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction updatedTransactionData = transactionService.updateTransaction(id, transaction);

        ApiResponse<Transaction> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction Updated successfully", updatedTransactionData);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    /**
     * Delete a transaction by its ID.
     *
     * @param id The ID of the transaction to delete.
     * @throws TransactionNotFoundException if the transaction does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        ApiResponse<Void> successResponse =
                new ApiResponse<>(ApiResponse.Status.SUCCESS, "Transaction Deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);

    }
}
