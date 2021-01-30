package com.example.kino.service.impl;

import com.example.kino.config.ModelMapperConfig;
import com.example.kino.config.dto.NewsDto;
import com.example.kino.entity.film.SEOInfo;
import com.example.kino.entity.news.News;
import com.example.kino.exeption.ResourceNotFoundException;
import com.example.kino.repo.NewsRepository;
import com.example.kino.service.api.ImageService;
import com.example.kino.service.api.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;


    @Override
    public NewsDto createNews(NewsDto request) {
        News news = modelMapper.map(request, News.class);
        newsRepository.save(news);
        log.info("News has been saved  = " + request.getName());

        return modelMapper.map(news, NewsDto.class);
    }

    @Override
    public NewsDto updateNews(NewsDto request) {
        News newsToUpdate = newsRepository.findById(request.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("News doesn't exists"));
        updateFields(newsToUpdate, request);
        newsRepository.save(newsToUpdate);
        return modelMapper.map(newsToUpdate, NewsDto.class);
    }

    private void updateFields(News newsToUpdate, NewsDto request) {
        newsToUpdate.setName(request.getName());
        newsToUpdate.setActive(request.isActive());
        newsToUpdate.setDescription(request.getDescription());
        newsToUpdate.setImages(request.getImages());
        newsToUpdate.setMainImage(request.getMainImage());
        newsToUpdate.setVideo(request.getVideo());
        newsToUpdate.setPublishDate(LocalDate.parse(request.getPublishDate(), ModelMapperConfig.dateFormatter));

        SEOInfo seoInfo = newsToUpdate.getSeoInfo();
        if(request.getSeoInfo() == null){
            newsToUpdate.setSeoInfo(null);
        } else{
            if(seoInfo==null) {
                seoInfo = new SEOInfo();
            }
            seoInfo.setDescription(request.getSeoInfo().getDescription());
            seoInfo.setKeywords(request.getSeoInfo().getKeywords());
            seoInfo.setTitle(request.getSeoInfo().getTitle());
            seoInfo.setUrl(request.getSeoInfo().getUrl());
            newsToUpdate.setSeoInfo(seoInfo);
        }
    }

    @Override
    public NewsDto getNewsById(long id) {
        News newsToUpdate = newsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("News doesn't exists"));
        return modelMapper.map(newsToUpdate, NewsDto.class);
    }

    @Override
    public Page<NewsDto> getAllNews(Pageable pageable) {
        Page<News> news = newsRepository.findAll(pageable);
        return news.map( n -> modelMapper.map(n, NewsDto.class));
    }

    @Override
    public void deleteNews(long id) {
        News newsToUpdate = newsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("News doesn't exists"));
        deleteImage(newsToUpdate.getMainImage());
        newsToUpdate.getImages().forEach(this::deleteImage);
        newsRepository.delete(newsToUpdate);
    }

    private void deleteImage(String image) {
        if (image!=null){
            imageService.deleteImage(image);
        }
    }
}
