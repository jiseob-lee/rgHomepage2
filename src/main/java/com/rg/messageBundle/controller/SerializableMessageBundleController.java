package com.rg.messageBundle.controller;

import java.util.Locale;
import java.util.Properties;

import jakarta.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SerializableMessageBundleController {

    @Inject
    SerializableResourceBundleMessageSource messageBundle;

    /**
     * ReadAll
     */
    @RequestMapping(value = "/rest/messageBundle", method=RequestMethod.GET)
    @ResponseBody
    public Properties list(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }
}
