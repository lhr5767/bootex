package com.minip3.movie.repository;

import com.minip3.movie.entity.Member;
import com.minip3.movie.entity.Movie;
import com.minip3.movie.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews() {

        IntStream.rangeClosed(1,200).forEach(i->{

            Long mno = (long)(Math.random()*100) + 1; //영화 번호

            Long mid = ((long)(Math.random()*100) +1 ); // 리뷰어 번호

            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5) +1)
                    .text("이 영화에 대한 느낌은" + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void testGetMovieReviews() {

        Movie movie = Movie.builder().mno(3L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println(movieReview.getGrade());
            System.out.println(movieReview.getText());
            System.out.println(movieReview.getMember().getEmail());
            System.out.println("================================");
        });
    }
}
