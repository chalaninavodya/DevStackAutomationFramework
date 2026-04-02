package com.devstack.automation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginTestData {
    private String role;
    private String email;
    private String password;
    private String expectedResult;
    private String validationType;
    private String testCaseName;
}
