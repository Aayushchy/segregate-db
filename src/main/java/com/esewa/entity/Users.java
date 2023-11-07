package com.esewa.entity;

import com.esewa.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("email_address")
    private String emailAddress;
    @JsonProperty("phone_number")
    private String phoneNumber;
//    private List<MultipartFile> files;
    @JsonProperty("verification_status")
    @Enumerated(EnumType.STRING)
    private Status verificationStatus;

}
