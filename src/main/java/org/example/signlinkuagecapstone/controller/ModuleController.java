package org.example.signlinkuagecapstone.controller;

import jakarta.validation.Valid;
import org.example.signlinkuagecapstone.dto.ModuleRequest;
import org.example.signlinkuagecapstone.entity.Module;
import org.example.signlinkuagecapstone.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Module> createModule(@Valid @RequestBody ModuleRequest request) {
        Module created = moduleService.createModule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Module>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }

    // READ ONE
    @GetMapping("/{moduleId}")
    public ResponseEntity<Module> getModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(moduleService.getModuleById(moduleId));
    }

    // UPDATE
    @PutMapping("/{moduleId}")
    public ResponseEntity<Module> updateModule(
            @PathVariable Long moduleId,
            @Valid @RequestBody ModuleRequest request) {

        return ResponseEntity.ok(moduleService.updateModule(moduleId, request));
    }

    // DELETE
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity.noContent().build();
    }
}
