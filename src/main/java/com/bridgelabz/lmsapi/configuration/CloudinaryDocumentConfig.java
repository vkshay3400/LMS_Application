package com.bridgelabz.lmsapi.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryDocumentConfig {

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", System.getenv().get("cloud_name"));
        config.put("api_key", System.getenv().get("api_key"));
        config.put("api_secret", System.getenv().get("api_secret"));
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
