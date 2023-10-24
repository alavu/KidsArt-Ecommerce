package com.kidsart.library.model;

import com.kidsart.library.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "wallets_history")
public class WalletHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_history_id")
    private Long id;

    private double amount;

    private WalletTransactionType type;

    private String transactionStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id",referencedColumnName = "wallet_id")
    private Wallet wallet;
}
