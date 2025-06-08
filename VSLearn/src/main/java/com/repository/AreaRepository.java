package com.repository;

import com.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByAreaName(@Size(max = 255) @NotNull String areaName);

    boolean existsByAreaName(@Size(max = 255) @NotNull String areaName);
} 