package ch.bbzw.m151.swshop.service;

import ch.bbzw.m151.swshop.dto.ProducerWithMerch;
import ch.bbzw.m151.swshop.model.Merch;
import ch.bbzw.m151.swshop.model.Producer;
import ch.bbzw.m151.swshop.repo.MerchRepo;
import ch.bbzw.m151.swshop.repo.ProducerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProducerService {
    private final ProducerRepo producerRepo;
    private final MerchRepo merchRepo;

    public ProducerService(final ProducerRepo producerRepo, final MerchRepo merchRepo) {
        this.producerRepo = producerRepo;
        this.merchRepo = merchRepo;
    }

    @Transactional(readOnly = true)
    public List<Producer> getAll() {
        return StreamSupport.stream(
                producerRepo.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(final Producer producer) {
        this.producerRepo.save(producer);
    }

    @Transactional
    public void saveWithMerch(final ProducerWithMerch producerWithMerch) {
        final List<Long> merchIds = producerWithMerch.getMerch();
        final Set<Merch> merchSet = StreamSupport.stream(merchRepo.findAllById(merchIds).spliterator(), true).collect(Collectors.toSet());
        final Producer producer = new Producer(producerWithMerch.getName(), merchSet);
        producerRepo.save(producer);
    }

    @Transactional
    public Optional<Producer> update(final long id, final ProducerWithMerch producerWithMerch) {
        final List<Long> merchIds = producerWithMerch.getMerch();
        final Set<Merch> merchSet = StreamSupport.stream(merchRepo.findAllById(merchIds).spliterator(), true).collect(Collectors.toSet());
        final Optional<Producer> optionalProducer = producerRepo.findById(id);
        if (optionalProducer.isPresent()) {
            final Producer producer = optionalProducer.get();
            producer.setMerch(merchSet);
            return Optional.of(producerRepo.save(producer));
        }
        return Optional.empty();
    }

    @Transactional
    public Producer custom() {
        return producerRepo.customQuery();
    }
}
