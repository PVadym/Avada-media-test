package com.example.kino.endpoint.admin;

import com.example.kino.config.dto.NewsDto;
import com.example.kino.service.api.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class NewsController {

    private final NewsService newsService;

    @PostMapping("/create")
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto request) {
        return ResponseEntity.ok(newsService.createNews(request));
    }
    @PutMapping("/update")
    public ResponseEntity<NewsDto> updateNews(@RequestBody NewsDto request){
        return ResponseEntity.ok(newsService.updateNews(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNews(@PathVariable long id){
        newsService.deleteNews(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNews(@PathVariable long id){
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NewsDto>> getNews(
            @PageableDefault(size = 10, page = 0)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<NewsDto> result = newsService.getAllNews(pageable);
        return ResponseEntity.ok(result);
    }
}
