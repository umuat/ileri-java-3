package com.library.service.impl;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Yazar servis implementasyonu
 * 
 * Bu sınıf AuthorService interface'ini implement eder.
 */
@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author, Long> implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Author> findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    @Override
    public List<Author> findByNationality(String nationality) {
        return authorRepository.findByNationality(nationality);
    }

    @Override
    public List<Author> findByBirthYearBetween(Integer startYear, Integer endYear) {
        return authorRepository.findByBirthYearBetween(startYear, endYear);
    }

    @Override
    public List<Author> findMostProlificAuthors(int limit) {
        return authorRepository.findMostProlificAuthors(limit);
    }
} 