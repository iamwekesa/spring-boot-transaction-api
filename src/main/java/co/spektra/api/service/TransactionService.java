package co.spektra.api.service;

import co.spektra.api.repository.TransactionRepository;
import co.spektra.api.model.Transaction;
import co.spektra.api.exception.TransactionNotFoundException;
import co.spektra.api.validation.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElseThrow((() ->
                new TransactionNotFoundException("Transaction not found with ID: " + id)));
    }

    public Transaction createTransaction(Transaction transaction){
        TransactionValidator.validate(transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction){
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + id));

        TransactionValidator.validate(updatedTransaction);

        existingTransaction.setSenderName(updatedTransaction.getSenderName());
        existingTransaction.setReceiverName(updatedTransaction.getReceiverName());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());

        return transactionRepository.save(existingTransaction);
    }
    public void deleteTransaction(Long id){
        if(!transactionRepository.existsById(id)) throw new TransactionNotFoundException("Transaction not found with ID: " + id);
        transactionRepository.deleteById(id);
    }
}
