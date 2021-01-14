package com.phani.samples.banking.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@ToString
@EqualsAndHashCode
@Entity
@Table
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String accountNumber;

  @Column
  private String type;

  @Column
  private double currentBalance;

  @Column
  private double beginningBalance;

  @Column
  private double interestEarned;

  @Version
  private Integer version;

  public Account() {
  }
}