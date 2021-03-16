package com.dhxxn.calendary.Repository;

import com.dhxxn.calendary.DTO.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary,Integer> {

}
