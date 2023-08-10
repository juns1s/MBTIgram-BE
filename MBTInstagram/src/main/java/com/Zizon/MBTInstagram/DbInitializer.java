package com.Zizon.MBTInstagram;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.global.MbtiType;
import com.Zizon.MBTInstagram.repository.MbtiViewsRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInitializer {

    @Bean
    public ApplicationRunner initializeDatabase(MbtiViewsRepository mbtiViewsRepository) {
        return args -> {
            // MbitVies 엔티티 초기화
            for (MbtiType type : MbtiType.values()) {
                mbtiViewsRepository.save(new MbtiViews(type));
            }
        };
    }
}
