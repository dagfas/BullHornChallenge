package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Post, Long> {

}