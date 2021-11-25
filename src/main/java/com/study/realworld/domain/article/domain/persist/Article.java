package com.study.realworld.domain.article.domain.persist;

import com.study.realworld.domain.article.domain.vo.ArticleBody;
import com.study.realworld.domain.article.domain.vo.ArticleDescription;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.domain.vo.ArticleTitle;
import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long articleId;

    @Embedded
    private ArticleSlug articleSlug;

    @Embedded
    private ArticleTitle articleTitle;

    @Embedded
    private ArticleBody articleBody;

    @Embedded
    private ArticleDescription articleDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false, updatable = false)
    private User author;

    @Builder
    public Article(final ArticleSlug articleSlug, final ArticleTitle articleTitle,
                   final ArticleBody articleBody, final ArticleDescription articleDescription,
                   final User author) {
        this.articleSlug = articleSlug;
        this.articleTitle = articleTitle;
        this.articleBody = articleBody;
        this.articleDescription = articleDescription;
        this.author = author;
    }

    public Long articleId() {
        return articleId;
    }

    public ArticleSlug articleSlug() {
        return articleSlug;
    }

    public ArticleTitle articleTitle() {
        return articleTitle;
    }

    public ArticleBody articleBody() {
        return articleBody;
    }

    public ArticleDescription articleDescription() {
        return articleDescription;
    }

    public User author() {
        return author;
    }

    public Article changeAuthor(final User author) {
        this.author = author;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Article article = (Article) o;
        return Objects.equals(articleId(), article.articleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId());
    }
}