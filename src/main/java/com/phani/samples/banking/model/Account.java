package com.phani.samples.banking.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

  //TODO - We will be the constraints to fields as necessary
  @NotBlank(message = "Account Number cannot be empty")
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