package com.project.ecommerce.util;

import java.util.HashMap;
import java.util.Map;

public class ValidationResponse {
  private Boolean isValid;
  private Map<String, String> errors = new HashMap<>();

  public ValidationResponse() {
  }

  public ValidationResponse(Boolean isValid, Map<String, String> errors) {
    this.isValid = isValid;
    this.errors = errors;
  }

  public Boolean getValid() {
    return isValid;
  }

  public void setValid(Boolean valid) {
    isValid = valid;
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }
}
