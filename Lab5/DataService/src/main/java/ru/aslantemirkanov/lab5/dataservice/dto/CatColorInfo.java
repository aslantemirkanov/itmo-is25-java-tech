package ru.aslantemirkanov.lab5.dataservice.dto;

import lombok.Data;
import ru.aslantemirkanov.lab5.dataservice.entities.CatColor;

import java.io.Serializable;

@Data
public class CatColorInfo implements Serializable {
    CatColor catColor;
    Long userId;

    public CatColorInfo(CatColor catColor, Long userId){
        this.catColor = catColor;
        this.userId = userId;
    }

}
