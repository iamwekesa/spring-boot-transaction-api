package co.spektra.api.validation;

import co.spektra.api.exception.InvalidTransactionException;
import co.spektra.api.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionValidator {
    private static final Integer MAX_NAME_LENGTH = 40;
    public static void validate(Transaction transaction) {
        validateSenderName(transaction.getSenderName());
        validateReceiverName(transaction.getReceiverName());
        validateAmount(transaction.getAmount());
        validateTransactionDate(transaction.getTransactionDate());
    }

    private static void validateSenderName(String senderName) {
        if (senderName == null || senderName.trim().isEmpty()) {
            throw new InvalidTransactionException("Sender name cannot be empty.");
        } else if (senderName.length() > MAX_NAME_LENGTH) {
            throw new InvalidTransactionException("Sender name is too long.");
        }
    }

    private static void validateReceiverName(String receiverName) {
        if (receiverName == null || receiverName.trim().isEmpty()) {
            throw new InvalidTransactionException("Receiver name cannot be empty.");
        } else if (receiverName.length() > MAX_NAME_LENGTH ) {
            throw new InvalidTransactionException("Receiver name is too long.");

        }
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be a positive value.");
        }
    }
    private static void validateTransactionDate(LocalDateTime transactionDate) {

        if (transactionDate == null) {
            throw new InvalidTransactionException("Transaction date cannot be empty.");
        }

        // TODO: Look into validating if Date is an acceptable Date.
    }
}
