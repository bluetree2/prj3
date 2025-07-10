package com.example.backend.member.dto;

import lombok.Data;

@Data
public class MemberFrom {
    private String email;
    private String password;
    private String nickName;
    private String info;
}

