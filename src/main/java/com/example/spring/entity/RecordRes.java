package com.example.spring.entity;

import lombok.Data;

@Data
public class RecordRes extends Record{
    private  String username;
    private  String adminname;
    private  String goodstypename;
    private  String goodsname;
    private  String storagename;
}
