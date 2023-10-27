package com.monzo.androidtest.articles.model

import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.api.model.ApiArticleListResponse
import io.reactivex.Single
import java.util.*

class ArticleMapper {

    fun map(apiArticleListResponse: ApiArticleListResponse): List<Article> {
        val articles = ArrayList<Article>()

        for ((id, sectionId, sectionName, webPublicationDate, _, _, apiUrl, fields) in apiArticleListResponse.response.results) {

            var thumbnail: String
            if (fields == null) {
                thumbnail = ""
            } else {
                thumbnail = fields.thumbnail ?: ""
            }

            var headline: String
            if (fields == null) {
                headline = ""
            } else {
                headline = fields.headline ?: ""
            }

            articles.add(Article(id,
                    thumbnail,
                    sectionId,
                    sectionName,
                    webPublicationDate,
                    headline,
                    apiUrl))
        }

        return articles
    }

    fun mapSingleArticle(apiArticleResponse: ApiArticle): Article {
        val fields = apiArticleResponse.fields

        val thumbnail = fields?.thumbnail ?: ""
        val headline = fields?.headline ?: ""
        val sectionId = apiArticleResponse.sectionId ?: ""
        val sectionName = apiArticleResponse.sectionName ?: ""
        val webPublicationDate = apiArticleResponse.webPublicationDate
        val apiUrl = apiArticleResponse.apiUrl ?: ""
        val id = apiArticleResponse.id ?: ""

        val article = Article(id, thumbnail, sectionId, sectionName, webPublicationDate, headline, apiUrl)

        return article
    }
}
