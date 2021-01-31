package com.example.kino.service.api;

import com.example.kino.config.dto.FilmAdminDto;
import com.example.kino.config.dto.FilmDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for film logic
 */
public interface FilmService {

    List<String> getFilmTypes();

    FilmAdminDto createFilm(FilmAdminDto request);

    List<String> getFilmStatuses();

    String changeFilmStatus(long id, String status);

    Page<FilmAdminDto> getAllFilms(String status, Pageable pageable);

    FilmAdminDto updateFilm(FilmAdminDto request);

    FilmAdminDto getFilmAdminById(long id);

    FilmDto getFilmById(long id);

    Page<FilmDto> getShowingFilms(Pageable pageable);
}
