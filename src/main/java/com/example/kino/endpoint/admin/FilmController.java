package com.example.kino.endpoint.admin;

import com.example.kino.config.dto.FilmDto;
import com.example.kino.service.api.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllFilmTypes(){
        return ResponseEntity.ok(filmService.getFilmTypes());
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllFilmStatuses(){
        return ResponseEntity.ok(filmService.getFilmStatuses());
    }

    @PostMapping("/create")
    public ResponseEntity<FilmDto> createFilm(@RequestBody FilmDto request) {
        return ResponseEntity.ok(filmService.createFilm(request));
    }

    @PutMapping("{id}/change-status/{status}")
    public ResponseEntity<String> changeStatusFilm(@RequestParam String status, @RequestParam long id) {
        return ResponseEntity.ok(filmService.changeFilmStatus(id,status));
    }

    @GetMapping
    public ResponseEntity<Page<FilmDto>> getFilms(
            @RequestParam(value = "status", required = false) String status,
            @PageableDefault(size = 10, page = 0)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<FilmDto> result = filmService.getAllFilms(status, pageable);
        return ResponseEntity.ok(result);
    }
}
