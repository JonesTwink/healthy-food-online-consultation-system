package com.meal.service;

import com.meal.entity.ArticleEntity;

public interface ArticleService {

  ArticleEntity findOne(int id);
  ArticleEntity createArticle(ArticleEntity topic);
  ArticleEntity updateArticle(ArticleEntity topic);
  void deleteArticle(int id);

  Iterable<ArticleEntity> findByPage(int page);
  Iterable<ArticleEntity> getArticlesByCoachId(int id);

}