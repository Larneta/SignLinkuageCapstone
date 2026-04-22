package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.dto.DashboardResponse;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.entity.UserModuleProgress;
import org.example.signlinkuagecapstone.repository.UserModuleProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final UserModuleProgressRepository moduleProgressRepository;

    public DashboardService(UserModuleProgressRepository moduleProgressRepository) {
        this.moduleProgressRepository = moduleProgressRepository;
    }

    public List<DashboardResponse> getUserDashboard(User user) {

        return moduleProgressRepository
                .findAll()
                .stream()
                .filter(progress ->
                        progress.getUser().getId().equals(user.getId()))
                .map(this::toDashboardResponse)
                .toList();
    }

    private DashboardResponse toDashboardResponse(UserModuleProgress progress) {

        return new DashboardResponse(
                progress.getModule().getId(),
                progress.getModule().getTitle(),
                progress.getModule().getDescription(),
                progress.getProgressPercent(),
                progress.getLessonsCompleted(),
                progress.getTotalLessons(),
                progress.getCompleted()
        );
    }
}

