package com.ssg.bidssgket.global.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        // 콤마 제거 후 변환
        return Integer.parseInt(source.replace(",", ""));
    }
}
