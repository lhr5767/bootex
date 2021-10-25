package com.minip3.movie.repository;

import com.minip3.movie.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
