package com.example.kino.endpoint.user;

import com.example.kino.config.dto.FilmDto;
import com.example.kino.service.api.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;


    @GetMapping("/showing")
    public ResponseEntity<Page<FilmDto>> getFilms(
            @PageableDefault(size = 12, page = 0)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<FilmDto> result = filmService.getShowingFilms(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable long id){
        return ResponseEntity.ok(filmService.getFilmById(id));
    }
}
