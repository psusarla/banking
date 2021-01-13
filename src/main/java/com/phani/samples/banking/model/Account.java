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

  /* Assumption
     - a customer can have many accounts
     - an account can be linked to many customers
  */

  @JoinTable(
          name = "customer_accounts",
          joinColumns = @JoinColumn(
                  name = "account_id",
                  referencedColumnName = "id"
          ),
          inverseJoinColumns = @JoinColumn(
                  name = "customer_id",
                  referencedColumnName = "id"
          )
  )
  @OneToMany(fetch=FetchType.LAZY)
  private List<Customer> customers;

  public Account() {
  }
}