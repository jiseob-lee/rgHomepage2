package com.rg.goAccess.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ConfigHolder {

    @Value("${goaccessPath}")
    private String path; // 인스턴스 필드로 주입 받음

    public static String goaccessPath;

    @PostConstruct
    public void init() {
        goaccessPath = path; // static 필드에 복사
    }
}