package com.Zizon.MBTInstagram.repository;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.global.MbtiType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MbtiTypeRepositoryTest {
    @Autowired
    private MbtiViewsRepository mbtiTypeRepository;

    @Test
    public void mbti전체검색(){
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
}