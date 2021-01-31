package com.example.kino.config;

import com.example.kino.config.dto.CreateUserRequest;
import com.example.kino.config.dto.FilmAdminDto;
import com.example.kino.config.dto.UserDto;
import com.example.kino.entity.film.Film;
import com.example.kino.entity.user.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    public static final DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(User.class, UserDto.class)
                .setPostConverter(MappingContext::getDestination);
        mapper.createTypeMap(UserDto.class, User.class)
                .setPostConverter(MappingContext::getDestination);
        mapper.createTypeMap(CreateUserRequest.class, User.class)
                .setPostConverter(MappingContext::getDestination);

        mapper.createTypeMap(Film.class, FilmAdminDto.class)
                .setPostConverter(MappingContext::getDestination);
        mapper.createTypeMap(FilmAdminDto.class, Film.class)
                .setPostConverter(MappingContext::getDestination);


        Converter<LocalDateTime, String> localDateTimeStringConverter = new AbstractConverter<LocalDateTime, String>() {
            protected String convert(LocalDateTime source) {
                return source == null ? null : source.format(time_formatter);
            }
        };

        Converter<String, LocalDateTime> stringToLocalDateTimeConverter = new AbstractConverter<String, LocalDateTime>() {
            protected LocalDateTime convert(String source) {
                return source == null ? null : LocalDateTime.parse(source, time_formatter);
            }
        };

        Converter<LocalDate, String> localDateStringConverter = new AbstractConverter<LocalDate, String>() {
            protected String convert(LocalDate source) {
                return source == null ? null : source.format(dateFormatter);
            }
        };

        Converter<String, LocalDate> stringToLocalDateConverter = new AbstractConverter<String, LocalDate>() {
            protected LocalDate convert(String source) {
                return source == null ? null : LocalDate.parse(source, dateFormatter);
            }
        };
        mapper.addConverter(localDateTimeStringConverter);
        mapper.addConverter(stringToLocalDateTimeConverter);

        mapper.addConverter(localDateStringConverter);
        mapper.addConverter(stringToLocalDateConverter);

        return mapper;
    }

}
