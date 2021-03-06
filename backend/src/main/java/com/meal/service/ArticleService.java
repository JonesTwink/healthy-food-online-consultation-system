package com.meal.service;

import com.meal.entity.ArticleEntity;
import com.meal.service.Exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {

  Page<ArticleEntity> findAll(int page, int pageSize);
  ArticleEntity findOne(int id);
  ArticleEntity createArticle(ArticleEntity topic) throws ServiceException;
  ArticleEntity updateArticle(ArticleEntity topic);
  void deleteArticle(int id);

  Iterable<ArticleEntity> getArticlesByCoachId(int id);

}
