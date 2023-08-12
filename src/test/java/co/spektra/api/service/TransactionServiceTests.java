package co.spektra.api.service;


import co.spektra.api.exception.TransactionNotFoundException;
import co.spektra.api.model.Transaction;
import co.spektra.api.repository.TransactionRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTests {
	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRepository transactionRepository;
	@Test
	public void shouldReturnAllTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		Integer pageNumber = 0;
		Integer pageSize = 10;

		Transaction transaction1 = new Transaction(
				1L,
				"Sender 1",
				"Receiver 1",
				new BigDecimal("100.00"), LocalDateTime.now());

		Transaction transaction2 = new Transaction(
				2L,
				"Sender 2",
				"Receiver 2",
				new BigDecimal("150.00"),LocalDateTime.now());

		transactions.add(transaction1);
		transactions.add(transaction2);

		Page<Transaction> transactionPage = new PageImpl<>(transactions);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		// Mock repository behavior
		Mockito.when(transactionRepository.findAll(pageable)).thenReturn(transactionPage);

		Page<Transaction> result = transactionService.getAllTransactions(pageNumber, pageSize);
		Assert.assertEquals(transactions.size(), result.getSize());
	}
	@Test
	public void shouldReturnTransactionById() {
		Long id = 1L;

		Transaction mockTransaction = new Transaction(
				1L,
				"Sender 3 John Doe",
				"Receiver 3 Jane Doe",
				new BigDecimal("1400.00"), LocalDateTime.now());


		Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(mockTransaction));

		Transaction result = transactionService.getTransactionById(id);

		Assert.assertEquals(mockTransaction, result);
	}

	@Test(expected = TransactionNotFoundException.class)
	public void shouldThrowExceptionForNonExistentTransaction() {
		Long id = 2L;
		Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.empty());

		transactionService.getTransactionById(id);
	}
	@Test
	public void shouldCreateTransaction() {

		Transaction transaction = new Transaction(
				1L,
				"Sender 4 John Doe",
				"Receiver 4 Jane Doe",
				new BigDecimal("5600.00"), LocalDateTime.now());

		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

		Transaction result = transactionService.createTransaction(transaction);

		Assert.assertEquals(transaction, result);
	}

	@Test
	public void shouldUpdateTransaction() {
		long id = 1L;
		Transaction existingTransaction = new Transaction(
				id,
				"Sender 5",
				"Receiver 5",
				new BigDecimal("100.00"), LocalDateTime.now());

		Transaction updatedTransaction = new Transaction(
				id,
				"Sender 5",
				"Receiver 5",
				new BigDecimal("900.00"),LocalDateTime.now());


		Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(existingTransaction));
		Mockito.when(transactionRepository.save(existingTransaction)).thenReturn(existingTransaction);

		Transaction result = transactionService.updateTransaction(id, updatedTransaction);


		Assert.assertEquals(updatedTransaction.getSenderName(), result.getSenderName());
		Assert.assertEquals(updatedTransaction.getReceiverName(), result.getReceiverName());
		Assert.assertEquals(updatedTransaction.getAmount(), result.getAmount());
		Assert.assertEquals(updatedTransaction.getTransactionDate(), result.getTransactionDate());
		Assert.assertEquals(existingTransaction, result);
	}
	@Test(expected = TransactionNotFoundException.class)
	public void shouldThrowExceptionWhenUpdatingNonExistentTransaction() {
		Long id = 9L;
		Transaction updatedTransaction = new Transaction(
				id,
				"Sender 6",
				"Receiver 5",
				new BigDecimal("900.00"),LocalDateTime.now());

		Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.empty());

		transactionService.updateTransaction(id, updatedTransaction);
	}
	@Test
	public void shouldDeleteTransaction() {
		Long id = 1L;
		Mockito.when(transactionRepository.existsById(id)).thenReturn(true);

		transactionService.deleteTransaction(id);

		Mockito.verify(transactionRepository, Mockito.times(1)).deleteById(id);
	}

	@Test(expected = TransactionNotFoundException.class)
	public void shouldThrowExceptionWhenDeletingNonExistentTransaction() {
		Long id = 2L;
		Mockito.when(transactionRepository.existsById(id)).thenReturn(false);

		transactionService.deleteTransaction(id);
	}

}
