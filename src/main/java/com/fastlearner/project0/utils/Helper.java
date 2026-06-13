package com.fastlearner.project0.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Helper
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}
