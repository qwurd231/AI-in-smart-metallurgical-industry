package com.knf.dev.demo.crudapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "data_table")
//@org.hibernate.annotations.NamedNativeQuery(name = "Data.findAll", query = "SELECT * FROM dataTable", resultClass = Data.class)
@Data
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    private double param1;
    private double param2;
    private double param3;
    private double param4;
    private double param5;
    private double param6;
    private double param7;
    private double param8;
    private double param9;
    private double param10;
    private double param11;
    private double param12;
    private double param13;
    private double param14;
    private double output1;
    private double output2;
    private double output3;
    private boolean isProcessed;
    private String selectedModel;

    public DataSet() {
    }

}
