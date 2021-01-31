package com.example.kino.service.impl;

import com.example.kino.config.dto.FilmAdminDto;
import com.example.kino.config.dto.FilmDto;
import com.example.kino.entity.film.Film;
import com.example.kino.entity.film.FilmStatus;
import com.example.kino.entity.film.FilmType;
import com.example.kino.entity.film.SEOInfo;
import com.example.kino.exeption.ResourceNotFoundException;
import com.example.kino.repo.FilmRepository;
import com.example.kino.service.api.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<String> getFilmTypes() {
        return Arrays.stream(FilmType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public FilmAdminDto createFilm(FilmAdminDto request) {
        Film newFilm = modelMapper.map(request, Film.class);
        newFilm.setStatus(FilmStatus.NOT_ACTIVE);
        filmRepository.save(newFilm);
        log.info("Film has been saved  = " + request.getName());

        return modelMapper.map(newFilm, FilmAdminDto.class);
    }

    @Override
    public List<String> getFilmStatuses() {
        return Arrays.stream(FilmStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public String changeFilmStatus(long id, String status) {
        Film film = getFilmFromRepoById(id);
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
    public Page<FilmAdminDto> getAllFilms(String status, Pageable pageable) {
        FilmStatus filmStatus = null;
        if(status!=null){
            filmStatus = retrieveStatus(status);
        }
        Specification<Film> specification = getSpecification(filmStatus);

        Page<Film> films = filmRepository.findAll(specification, pageable);
        return films.map( film -> modelMapper.map(film, FilmAdminDto.class));
    }

    @Override
    public FilmAdminDto updateFilm(FilmAdminDto request) {
        Film filmToUpdate = getFilmFromRepoById(request.getId());

        updateFields(filmToUpdate, request);
        filmRepository.save(filmToUpdate);
        return modelMapper.map(filmToUpdate, FilmAdminDto.class);
    }

    private Film getFilmFromRepoById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Film doesn't exists"));
    }

    @Override
    public FilmAdminDto getFilmAdminById(long id) {
        return modelMapper.map(getFilmFromRepoById(id), FilmAdminDto.class);
    }

    @Override
    public FilmDto getFilmById(long id) {
        return modelMapper.map(getFilmFromRepoById(id), FilmDto.class);
    }

    @Override
    public Page<FilmDto> getShowingFilms(Pageable pageable) {
        Specification<Film> specification = getSpecification(FilmStatus.ACTIVE);
        Page<Film> films = filmRepository.findAll(specification, pageable);
        return films.map( film -> modelMapper.map(film, FilmDto.class));
    }

    private Specification<Film> getSpecification(FilmStatus filterByStatus) {
        return (Specification<Film>) (root, criteriaQuery, cb) -> {
            Predicate predicate = cb.conjunction();
            if(Objects.nonNull(filterByStatus)){
                predicate = cb.equal(cb.upper(root.get("status")),  filterByStatus);
            }
            return predicate;
        };
    }

    private void updateFields(Film film, FilmAdminDto request) {
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setImages(request.getImages());
        film.setMainImage(request.getMainImage());
        film.setTrailer(request.getTrailer());
        film.setType(FilmType.valueOf(request.getType()));
        if(request.getSeoInfo() == null){
            film.setSeoInfo(null);
        } else{
            SEOInfo seoInfo = film.getSeoInfo();
            if(Objects.isNull(seoInfo)) {
                seoInfo = new SEOInfo();
            }
            seoInfo.setDescription(request.getSeoInfo().getDescription());
            seoInfo.setKeywords(request.getSeoInfo().getKeywords());
            seoInfo.setTitle(request.getSeoInfo().getTitle());
            seoInfo.setUrl(request.getSeoInfo().getUrl());
            film.setSeoInfo(seoInfo);
        }
    }
}
