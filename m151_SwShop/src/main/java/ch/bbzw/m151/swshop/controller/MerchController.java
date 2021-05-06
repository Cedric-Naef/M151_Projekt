package ch.bbzw.m151.swshop.controller;

import ch.bbzw.m151.swshop.model.Merch;
import ch.bbzw.m151.swshop.service.MerchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/merch")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class MerchController {

    private final MerchService merchService;

    @Autowired
    public MerchController(final MerchService merchService) {
        this.merchService = merchService;
    }

    @GetMapping("/")
    public List<Merch> getAll() {
        return merchService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Merch> get(@PathVariable final long id) {
        return merchService.get(id);
    }

    @PostMapping("/")
    public Merch add(@RequestBody final Merch merch) {
        return merchService.add(merch);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        merchService.delete(id);
    }

    @DeleteMapping("/")
    public void deleteAll() {
        merchService.deleteAll();
    }
}
