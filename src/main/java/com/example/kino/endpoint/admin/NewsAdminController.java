package com.example.kino.endpoint.admin;

import com.example.kino.config.dto.NewsAdminDto;
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
@RequestMapping("/news/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class NewsAdminController {

    private final NewsService newsService;

    @PostMapping("/create")
    public ResponseEntity<NewsAdminDto> createNews(@RequestBody NewsAdminDto request) {
        return ResponseEntity.ok(newsService.createNews(request));
    }
    @PutMapping("/update")
    public ResponseEntity<NewsAdminDto> updateNews(@RequestBody NewsAdminDto request){
        return ResponseEntity.ok(newsService.updateNews(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNews(@PathVariable long id){
        newsService.deleteNews(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsAdminDto> getNewsById(@PathVariable long id){
        return ResponseEntity.ok(newsService.getAdminNewsById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NewsAdminDto>> getNews(
            @PageableDefault(size = 10, page = 0)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<NewsAdminDto> result = newsService.getAllAdminNews(pageable);
        return ResponseEntity.ok(result);
    }
}
