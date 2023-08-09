package com.Zizon.MBTInstagram.repository;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MbtiViewsRepository extends JpaRepository<MbtiViews, String> {

    public ArrayList<MbtiViews> findAll(Sort sort);
}
