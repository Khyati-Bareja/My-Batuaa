package com.myBatuaa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="wallet_records")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Wallet {
  
    @Id
    @Column(name="wallet_id",nullable = false, unique = true)
    private String walletId;
 //   @Column(name = "user_id")
   // private Integer userId;
    @OneToOne
    @JoinColumn(name = "user_id") // maps to User.userId
    private User user;
    
    private LocalDateTime createdAt;
    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;
}