package com.example.kino.service.api;

import com.example.kino.config.dto.FilmDto;
import com.example.kino.entity.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmService {

    List<String> getFilmTypes();

    FilmDto createFilm(FilmDto request);

    List<String> getFilmStatuses();

    String changeFilmStatus(long id, String status);

    Page<FilmDto> getAllFilms(String status, Pageable pageable);

}
