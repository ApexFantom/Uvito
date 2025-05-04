package com.Uvito.Uvito.repository;

import com.Uvito.Uvito.models.UselessItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UselessItemsRepository extends JpaRepository<UselessItems, Long> {

    void deleteByName(String name);

    UselessItems findByName(String name);

}
