package com.example.kino.service.api;

import com.example.kino.config.dto.NewsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    NewsDto createNews(NewsDto request);

    NewsDto updateNews(NewsDto request);

    NewsDto getNewsById(long id);

    Page<NewsDto> getAllNews(Pageable pageable);

    void deleteNews(long id);
}
