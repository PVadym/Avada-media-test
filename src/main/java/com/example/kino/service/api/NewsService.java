package com.example.kino.service.api;

import com.example.kino.config.dto.NewsAdminDto;
import com.example.kino.config.dto.NewsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for news logic
 */
public interface NewsService {
    NewsAdminDto createNews(NewsAdminDto request);

    NewsAdminDto updateNews(NewsAdminDto request);

    NewsAdminDto getAdminNewsById(long id);

    Page<NewsAdminDto> getAllAdminNews(Pageable pageable);

    void deleteNews(long id);

    NewsDto getNewsById(long id);

    Page<NewsDto> getAllNews(Pageable pageable);
}
