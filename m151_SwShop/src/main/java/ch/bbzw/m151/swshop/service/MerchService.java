package ch.bbzw.m151.swshop.service;

import ch.bbzw.m151.swshop.model.Merch;
import ch.bbzw.m151.swshop.repo.MerchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@CacheConfig(cacheNames = {"merch"})
public class MerchService {

    private final MerchRepo merchRepo;

    @Autowired
    public MerchService(final MerchRepo merchRepo) {
        this.merchRepo = merchRepo;
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "0")
    public List<Merch> getAll() {
        final Iterable<Merch> merch = merchRepo.findAll();
        return StreamSupport
                .stream(merch.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(key = "#merch.id")
    @CacheEvict(key = "0")
    public Merch add(final Merch merch) {
        return merchRepo.save(merch);
    }

    @Caching(evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "0")})
    public void delete(final long id) {
        merchRepo.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    public void deleteAll() {
        merchRepo.deleteAll(merchRepo.findAll());
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#id", unless = "#result == null")
    public Optional<Merch> get(final long id) {
       return Optional.ofNullable(merchRepo.findById(id));
    }
}
