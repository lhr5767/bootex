package com.minip3.movie.repository;

import com.minip3.movie.entity.Member;
import com.minip3.movie.entity.Movie;
import com.minip3.movie.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    //특정영화에 대한 리뷰정보
    //Member도 같이 로딩 가능하도록 @EntityGraph 사용
    @EntityGraph(attributePaths = {"member"},type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.member =:member")
    void deleteByMember(Member member);
}
