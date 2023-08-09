package com.Zizon.MBTInstagram.domain;

import com.Zizon.MBTInstagram.global.MbtiType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MbtiViews {

    @Id
    @Enumerated(EnumType.STRING)
    private MbtiType type;
    private int count;

    public MbtiViews(MbtiType mbtiType){
        this.type = mbtiType;
        this.count = 0;
    }

    public void addCount(){
        this.count += 1;
    }
}
