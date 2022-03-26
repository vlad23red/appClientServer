package com.example.appclientserver.service;

import com.example.appclientserver.model.DataCSV;
import com.example.appclientserver.repositories.DataCSVsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataCSVService {
    @Autowired
    DataCSVsRepository dataCSVsRepository;

    public List<DataCSV> fetchAll() {
        return (List<DataCSV>) dataCSVsRepository.findAll();

    }
}
