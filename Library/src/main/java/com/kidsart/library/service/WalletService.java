package com.kidsart.library.service;

import com.kidsart.library.dto.WalletHistoryDto;
import com.kidsart.library.model.Customer;
import com.kidsart.library.model.Order;
import com.kidsart.library.model.Wallet;
import com.kidsart.library.model.WalletHistory;

import java.util.List;

public interface WalletService {

    List<WalletHistoryDto> findAllById(long id);

    Wallet findByCustomer(Customer customer);

    WalletHistory save(double amount, Customer customer);

    WalletHistory findById(long id);

    void updateWallet(WalletHistory walletHistory, Customer customer, boolean status);

    void debit(Wallet wallet, double totalPrice);

    void returnCredit(Order order, Customer customer);
}
