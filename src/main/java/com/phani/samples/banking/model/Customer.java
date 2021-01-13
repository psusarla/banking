package com.phani.samples.banking.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@EqualsAndHashCode
@Entity
@Table
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String address;

  @Column
  private String kycPhone;

  @Column
  private String kycProofOfAddress;

  @JoinTable(
          name = "customer_accounts",
          joinColumns = @JoinColumn(
                  name = "customer_id",
                  referencedColumnName = "id"
          ),
          inverseJoinColumns = @JoinColumn(
                  name = "account_id",
                  referencedColumnName = "id"
          )
  )
  @OneToMany(fetch=FetchType.LAZY)
  private List<Account> accounts = new ArrayList<>();

  public Customer() {
  }
}
