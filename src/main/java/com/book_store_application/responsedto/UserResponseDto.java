package com.book_store_application.responsedto;

import com.book_store_application.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



public class UserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private LocalDate registeredDate;
    private LocalDate updatedDate;
    private String password;
    private String emailId;
    private Role role;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }



    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserResponseDto(String token, long id, String firstName, String lastName, LocalDate dob, LocalDate registeredDate, LocalDate updatedDate, String password, String emailId, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.registeredDate = registeredDate;
        this.updatedDate = updatedDate;
        this.password = password;
        this.emailId = emailId;
        this.role = role;
    }

    public UserResponseDto() {
    }
}
