package com.t3h.ecommerce.dto.request.admin_customer;

public interface CustomerAdmin {
     Long getId();
     String getFullName();
     String getUserName();
     Integer getStatus();
     String getAddress();
     String getEmail();
     String getPhoneNumber();
     Integer getGender();
     Long getCreatedDate();
     Long getUpdatedDate();
}
