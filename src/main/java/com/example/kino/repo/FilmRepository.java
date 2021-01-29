package com.example.kino.repo;

import com.example.kino.entity.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Page<Film> findAll(Specification<Film> example, Pageable pageable);

}
