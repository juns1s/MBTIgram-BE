package com.Zizon.MBTInstagram.repository;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.global.MbtiType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MbtiViewsRepository extends JpaRepository<MbtiViews, String> {

    ArrayList<MbtiViews> findAll(Sort sort);
    Optional<MbtiViews> findByType(MbtiType mbtiType);
}
