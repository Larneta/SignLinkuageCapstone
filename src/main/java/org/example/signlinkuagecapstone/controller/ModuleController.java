package org.example.signlinkuagecapstone.controller;

import jakarta.validation.Valid;
import org.example.signlinkuagecapstone.dto.ModuleRequest;
import org.example.signlinkuagecapstone.dto.ModuleResponse;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.repository.UserRepository;
import org.example.signlinkuagecapstone.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;
    private final UserRepository userRepository;

    public ModuleController(ModuleService moduleService, UserRepository userRepository) {
        this.moduleService = moduleService;
        this.userRepository = userRepository;
    }

    // CREATE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ModuleResponse> createModule(@Valid @RequestBody ModuleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(moduleService.toModuleResponse(moduleService.createModule(request)));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ModuleResponse>> getAllModules(Authentication authentication) {
        return ResponseEntity.ok(moduleService.getAllModuleResponses(requireUser(authentication)));
    }

    // READ ONE
    @GetMapping("/{moduleId}")
    public ResponseEntity<ModuleResponse> getModule(@PathVariable Long moduleId, Authentication authentication) {
        return ResponseEntity.ok(moduleService.getModuleResponse(moduleId, requireUser(authentication)));
    }

    // UPDATE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{moduleId}")
    public ResponseEntity<ModuleResponse> updateModule(
            @PathVariable Long moduleId,
            @Valid @RequestBody ModuleRequest request) {

        return ResponseEntity.ok(
                moduleService.toModuleResponse(moduleService.updateModule(moduleId, request))
        );
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity.noContent().build();
    }

    private User requireUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + authentication.getName()));
    }
}
