package com.phani.samples.banking.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Error {
  private String message;
  private String id;
  private List<String> errors;
  private HttpStatus status;
}
