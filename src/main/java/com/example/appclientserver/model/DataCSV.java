package com.example.appclientserver.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "data_csv") // задаем имя таблици в БД
public class DataCSV implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String mccCode;
    private String trType;
    private String codeTrType;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public DataCSV(String mccCode, String trType, String codeTrType){
        super();

        this.mccCode = mccCode;
        this.trType = trType;
        this.codeTrType =codeTrType;

    }
    public String getMccCode(){
        return mccCode;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }

    public String getTrType() {
        return trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public String getCodeTrType() {
        return codeTrType;
    }

    public void setCodeTrType(String codeTrType) {
        this.codeTrType = codeTrType;
    }
}
