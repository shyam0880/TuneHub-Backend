package com.example.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.DBPing;

public interface DBPingRepo extends JpaRepository<DBPing, String>{

}
