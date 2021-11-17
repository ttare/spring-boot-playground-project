package com.example.imagesharingapi.repositories;

import com.example.imagesharingapi.models.dao.Album;
import com.example.imagesharingapi.models.dto.AlbumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("select a.id as id, a.name as name, a.createdAt as createdAt, count(i) as images from Album a join Image i on i.album.id = a.id where a.user.id = :userId group by a.id")
    public List<AlbumDto> findAllByUserIdAndCount(@Param("userId") Long userId);
}
