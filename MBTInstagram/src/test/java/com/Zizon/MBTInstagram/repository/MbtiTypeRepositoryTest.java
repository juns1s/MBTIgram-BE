package com.Zizon.MBTInstagram.repository;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.global.MbtiType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MbtiTypeRepositoryTest {
    @Autowired
    private MbtiViewsRepository mbtiTypeRepository;
    @PersistenceContext
    private EntityManager em;


    @Test
    public void mbti전체검색() throws Exception{
        //given
        MbtiViews entp = new MbtiViews(MbtiType.ENTP);
        MbtiViews entj = new MbtiViews(MbtiType.ENTJ);
        MbtiViews enfp = new MbtiViews(MbtiType.ENFP);

        entp.addCount();
        entp.addCount();
        entj.addCount();

        //when
        mbtiTypeRepository.save(enfp);
        mbtiTypeRepository.save(entj);
        mbtiTypeRepository.save(entp);
        ArrayList<MbtiViews> all = mbtiTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "count"));
        //then
        Assertions.assertThat(all.get(0).getType()).isEqualTo(MbtiType.ENTP);
        Assertions.assertThat(all.get(1).getType()).isEqualTo(MbtiType.ENTJ);
        Assertions.assertThat(all.get(2).getType()).isEqualTo(MbtiType.ENFP);
    }

    @Test
    @Transactional
    public void mbti증가() throws Exception{
        //Given
        MbtiViews entp = new MbtiViews(MbtiType.ENTP);
        mbtiTypeRepository.save(entp);
        em.flush();

        //When
        mbtiTypeRepository.updateCount(MbtiType.ENTP);
        em.flush();


        // Then: 업데이트된 count 값이 예상대로 15여야 함
        Optional<MbtiViews> updatedMbtiViews = mbtiTypeRepository.findByType(MbtiType.ENTP); // 엔티티 조회
        Assertions.assertThat(updatedMbtiViews.get().getCount()).isEqualTo(5);
    }
}