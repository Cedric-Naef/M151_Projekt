package ch.bbzw.m151.swshop.controller;

import ch.bbzw.m151.swshop.dto.ProducerWithMerch;
import ch.bbzw.m151.swshop.model.Producer;
import ch.bbzw.m151.swshop.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producers")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class ProducerController {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(final ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public List<Producer> all() {
        return producerService.getAll();
    }

    @PostMapping
    public void create(@RequestBody final Producer producer) {
        producerService.save(producer);
    }

    @PostMapping("/with-merch/")
    public void createWithMerch(@RequestBody final ProducerWithMerch producerWithMerch) {
        producerService.saveWithMerch(producerWithMerch);
    }

    @PutMapping(value = "/{id}")
    public Producer update(@PathVariable final long id, @RequestBody final ProducerWithMerch producerWithMerch) {
        return producerService.update(id, producerWithMerch).orElse(null);
    }

}
