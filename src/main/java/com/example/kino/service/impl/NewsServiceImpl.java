package com.example.kino.service.impl;

import com.example.kino.config.ModelMapperConfig;
import com.example.kino.config.dto.NewsAdminDto;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;


    @Override
    public NewsAdminDto createNews(NewsAdminDto request) {
        News news = modelMapper.map(request, News.class);
        newsRepository.save(news);
        log.info("News has been saved  = " + request.getName());

        return modelMapper.map(news, NewsAdminDto.class);
    }

    @Override
    public NewsAdminDto updateNews(NewsAdminDto request) {
        News newsToUpdate = getNews(request.getId());
        updateFields(newsToUpdate, request);
        newsRepository.save(newsToUpdate);
        return modelMapper.map(newsToUpdate, NewsAdminDto.class);
    }

    private void updateFields(News newsToUpdate, NewsAdminDto request) {
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
    public NewsAdminDto getAdminNewsById(long id) {
        News newsToUpdate = getNews(id);
        return modelMapper.map(newsToUpdate, NewsAdminDto.class);
    }

    private News getNews(long id) {
        return newsRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("News doesn't exists"));
    }

    @Override
    public Page<NewsAdminDto> getAllAdminNews(Pageable pageable) {
        Specification<News> specification = getSpecification(null);
        Page<News> news = newsRepository.findAll(specification,pageable);
        return news.map( n -> modelMapper.map(n, NewsAdminDto.class));
    }

    @Override
    public void deleteNews(long id) {
        News newsToUpdate = getNews(id);
        deleteImage(newsToUpdate.getMainImage());
        newsToUpdate.getImages().forEach(this::deleteImage);
        newsRepository.delete(newsToUpdate);
    }

    @Override
    public NewsDto getNewsById(long id) {
        News news = getNews(id);
        return modelMapper.map(news, NewsDto.class);
    }

    @Override
    public Page<NewsDto> getAllNews(Pageable pageable) {
        Specification<News> specification = getSpecification(true);
        Page<News> news = newsRepository.findAll(specification,pageable);
        return news.map( n -> modelMapper.map(n, NewsDto.class));
    }

    private void deleteImage(String image) {
        if (image!=null){
            imageService.deleteImage(image);
        }
    }

    private Specification<News> getSpecification(Boolean isActive) {
        return (Specification<News>) (root, criteriaQuery, cb) -> {
            Predicate predicate = cb.conjunction();
            if(Objects.nonNull(isActive)){
                predicate = cb.equal(root.get("active"), isActive);
            }
            return predicate;
        };
    }
}
