package com.rbacjava.models.dao;

import com.rbacjava.cbu.CbuGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(name = "uk_account_cbu", columnNames = "cbu")})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_account_user")
    )
    private User user;

    // Se genera al persistir
    @Column(name = "cbu", length = 22, nullable = false, updatable = false)
    private String cbu;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Account() {}

    public Account(User user, AccountType type) {
        this.user = user;
        this.type = type;
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.cbu = CbuGenerator.generate();
    }

    // Es prePersist porque el id no esta en la bd todavia
    @PrePersist
    private void onPrePersist() {
        if (this.cbu == null) {
            this.cbu = CbuGenerator.generate();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", cbu='" + cbu + "', type=" + type
                + ", balance=" + balance + "}";
    }
}