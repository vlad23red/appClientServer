package com.example.appclientserver.repositories;

import com.example.appclientserver.model.DataCSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCSVsRepository extends JpaRepository<DataCSV, Long> {
}
