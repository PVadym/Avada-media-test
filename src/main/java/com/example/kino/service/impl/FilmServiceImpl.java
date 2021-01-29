package com.example.kino.service.impl;

import com.example.kino.config.dto.FilmDto;
import com.example.kino.entity.film.Film;
import com.example.kino.entity.film.FilmStatus;
import com.example.kino.entity.film.FilmType;
import com.example.kino.exeption.ResourceNotFoundException;
import com.example.kino.repo.FilmRepository;
import com.example.kino.service.api.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<String> getFilmTypes() {
        return Arrays.stream(FilmType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDto createFilm(FilmDto request) {
        Film newFilm = modelMapper.map(request, Film.class);
        newFilm.setStatus(FilmStatus.NOT_ACTIVE);
        filmRepository.save(newFilm);
        log.info("Film has been saved  = " + request.getName());

        return modelMapper.map(newFilm, FilmDto.class);
    }

    @Override
    public List<String> getFilmStatuses() {
        return Arrays.stream(FilmStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public String changeFilmStatus(long id, String status) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Film doesn't exists"));
        FilmStatus newStatus = retrieveStatus(status);
        film.setStatus(newStatus);

        return status;
    }

    private FilmStatus retrieveStatus(String status) {
        try{
            return FilmStatus.valueOf(status);
        } catch (Exception e){
            log.info("Status doesn't exists  = " + status);
            throw  new ResourceNotFoundException("Status doesn't exists");
        }
    }


    @Override
    public Page<FilmDto> getAllFilms(String status, Pageable pageable) {
        Specification<Film> specification = getSpecification(status);

        Page<Film> films = filmRepository.findAll(specification, pageable);
        return films.map( film -> modelMapper.map(film, FilmDto.class));
    }

    private Specification<Film> getSpecification(String filterByStatus) {
        return (Specification<Film>) (root, criteriaQuery, cb) -> {
            Predicate predicate = cb.conjunction();
            if(Objects.nonNull(filterByStatus) && !filterByStatus.isEmpty()){
                predicate = cb.equal(cb.upper(root.get("status")),  retrieveStatus(filterByStatus));
            }
            return predicate;
        };
    }
}
