package com.readholics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="User")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    @NotBlank(message = "User name required!")
    private String userName;

    @Setter
    @Getter
    private String status;

    @Setter
    @Getter
    private Date renewalDate;


    public User(int userId,String userName, String status, Date renewalDate) {
        this.userName = userName;
        this.status = status;
        if(renewalDate != null) {
            this.renewalDate = renewalDate;
        }
        else{
            Calendar current_date= Calendar.getInstance();
            current_date.add(Calendar.YEAR,1);
            this.renewalDate= current_date.getTime();
        }
    }

    public User() {
        this.userName = "DefaultUserName";
        this.status = "Active";
    }
}
