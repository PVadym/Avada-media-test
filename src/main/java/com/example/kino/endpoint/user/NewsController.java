package com.example.kino.endpoint.user;

import com.example.kino.config.dto.NewsAdminDto;
import com.example.kino.config.dto.NewsDto;
import com.example.kino.service.api.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNews(@PathVariable long id){
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NewsDto>> getNews(
            @PageableDefault(size = 12, page = 0)
            @SortDefault(sort = "publishDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NewsDto> result = newsService.getAllNews(pageable);
        return ResponseEntity.ok(result);
    }
}
