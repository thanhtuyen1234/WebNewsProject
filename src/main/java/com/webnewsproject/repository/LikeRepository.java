package com.webnewsproject.repository;

import com.webnewsproject.domain.Likes;
import com.webnewsproject.model.TopPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Likes, Integer> {

    @Query(nativeQuery = true, value = "select count(like_id) from Likes where news_id = ?1")
    Integer likeNumber(int newsId);

    @Query("select l from Likes l where l.news.newsId=?1 and l.user.username=?2")
    Likes findLike(int newsId, String username);

//    @Query("select new com.webnewsproject.model.TopPost(n.title, count(Likes.likeId), max(Likes.likeDate), min(Likes.likeDate)) from Likes inner join News N on N.newsId = Likes.news.newsId group by N.title order by count(Likes.likeId) desc")
//    List<TopPost> getTopPost();
}
