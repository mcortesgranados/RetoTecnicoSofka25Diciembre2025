package com.mcg.sofka.retotecnicobanco.api.rest.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Locale;

/**
 * Captures transactions linked to an account, storing amounts, type, and resulting balance.
 */
@Entity
@Table(name = "movement", indexes = {
        @Index(name = "idx_movement_account", columnList = "account_id")
})
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id", nullable = false)
    private Long movementId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "movement_date", nullable = false)
    private OffsetDateTime movementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 10)
    private MovementType movementType;

    @Column(name = "description", length = 255)
    private String description = "";

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "balance_after", nullable = false, precision = 18, scale = 2)
    private BigDecimal balanceAfter;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public Long getMovementId() {
        return movementId;
    }

    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OffsetDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(OffsetDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum MovementType {
        DEBIT,
        CREDIT;

        public static MovementType from(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Movement type must not be null");
            }
            String normalized = value.trim().toUpperCase(Locale.ROOT);
            if (normalized.isEmpty()) {
                throw new IllegalArgumentException("Movement type must not be blank");
            }
            return MovementType.valueOf(normalized);
        }
    }
}
