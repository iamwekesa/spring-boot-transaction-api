package co.spektra.api.service;

import co.spektra.api.exception.InvalidTransactionException;
import co.spektra.api.model.Transaction;
import co.spektra.api.validation.TransactionValidator;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionValidatorTest {

    @Test
    public void shouldValidateValidTransaction() {
        Transaction validTransaction = new Transaction(
                1L,
                "Sender 1",
                "Receiver 1",
                new BigDecimal("100.00"),
                LocalDateTime.now());

        TransactionValidator.validate(validTransaction);
    }

    @Test(expected = InvalidTransactionException.class)
    public void shouldValidateEmptySenderName() {
        Transaction invalidTransaction = new Transaction(
                1L,
                "",
                "Receiver 1 Jane Doe",
                new BigDecimal("5100.00"),
                LocalDateTime.now());

        TransactionValidator.validate(invalidTransaction);
    }
    @Test(expected = InvalidTransactionException.class)
    public void shouldValidateEmptyReceiverName() {
        Transaction invalidTransaction = new Transaction(
                1L,
                "Sender 2 John Doe",
                "",
                new BigDecimal("2100.00"),
                LocalDateTime.now());

        TransactionValidator.validate(invalidTransaction);
    }
    @Test(expected = InvalidTransactionException.class)
    public void shouldValidateLongSenderName() {
        Transaction invalidTransaction = new Transaction(
                1L,
                "Sender 3 John Doe",
                "ThisSenderNameIsWayTooLongAndExceedsFortyCharacters",
                new BigDecimal("4100.00"),
                LocalDateTime.now());

        TransactionValidator.validate(invalidTransaction);
    }

    @Test(expected = InvalidTransactionException.class)
    public void shouldValidateNegativeAmount() {
        Transaction invalidTransaction = new Transaction(
                1L,
                "Sender 4 John Doe",
                "Receiver 4 Jane Doe",
                new BigDecimal("-8100.00"),
                LocalDateTime.now());

        TransactionValidator.validate(invalidTransaction);
    }

}
