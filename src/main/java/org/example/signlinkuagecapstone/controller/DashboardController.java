package org.example.signlinkuagecapstone.controller;

import org.example.signlinkuagecapstone.dto.DashboardResponse;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.repository.UserRepository;
import org.example.signlinkuagecapstone.service.DashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;





@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public DashboardController(DashboardService dashboardService, UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<DashboardResponse>> getDashboard(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                dashboardService.getUserDashboard(requireUser(authentication))
        );
    }

    private User requireUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + authentication.getName()));
    }
}

