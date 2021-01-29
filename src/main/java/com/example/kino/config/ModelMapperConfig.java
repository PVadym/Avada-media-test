package com.example.kino.config;

import com.example.kino.config.dto.CreateUserRequest;
import com.example.kino.config.dto.UserDto;
import com.example.kino.entity.user.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(User.class, UserDto.class)
                .setPostConverter(MappingContext::getDestination);

        mapper.createTypeMap(UserDto.class, User.class)
                .setPostConverter(MappingContext::getDestination);

        mapper.createTypeMap(CreateUserRequest.class, User.class)
                .setPostConverter(MappingContext::getDestination);


        Converter<LocalDateTime, String> localDateTimeStringConverter = new AbstractConverter<LocalDateTime, String>() {
            protected String convert(LocalDateTime source) {
                return source == null ? null : source.format(formatter);
            }
        };

        Converter<String, LocalDateTime> stringToLocalDateTimeConverter = new AbstractConverter<String, LocalDateTime>() {
            protected LocalDateTime convert(String source) {
                return source == null ? null : LocalDateTime.parse(source, formatter);
            }
        };
        mapper.addConverter(localDateTimeStringConverter);
        mapper.addConverter(stringToLocalDateTimeConverter);

        return mapper;
    }

}
