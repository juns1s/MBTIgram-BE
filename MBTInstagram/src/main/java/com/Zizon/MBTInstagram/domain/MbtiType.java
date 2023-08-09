package com.Zizon.MBTInstagram.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MbtiType {

    @Id
    private String type;
    private int count;

    public MbtiType(String mbtiType){
        this.type = mbtiType;
        this.count = 0;
    }

    public void addCount(){
        this.count += 1;
    }
}
