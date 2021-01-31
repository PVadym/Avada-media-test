package com.example.kino.service.impl;

import com.example.kino.config.dto.FilmDto;
import com.example.kino.entity.film.Film;
import com.example.kino.entity.film.FilmType;
import com.example.kino.repo.FilmRepository;
import com.example.kino.service.api.FilmService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class FilmServiceImplTest {

    @Autowired
    private FilmService filmService;

    @TestConfiguration
    static class Config {

        @Bean
        FilmService filmService() {
            return new FilmServiceImpl();
        }
    }

    @MockBean
    private FilmRepository filmRepository;
    @MockBean
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Film");
        film.setType(FilmType.D3);

        Mockito.when(filmRepository.findById(1L))
                .thenReturn(Optional.of(film));
    }


    @Test
    public void whenFindById_thenFilmShouldBeFound() {
        FilmDto found = filmService.getFilmById(1L);
        verify(filmRepository, times(1)).findById(1L);
    }

}
