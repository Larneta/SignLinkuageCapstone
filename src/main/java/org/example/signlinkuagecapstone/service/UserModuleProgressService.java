package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.entity.Lesson;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.entity.UserModuleProgress;
import org.example.signlinkuagecapstone.repository.UserModuleProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class UserModuleProgressService {

    private final UserModuleProgressRepository moduleProgressRepository;

    public UserModuleProgressService(
            UserModuleProgressRepository moduleProgressRepository
    ) {
        this.moduleProgressRepository = moduleProgressRepository;
    }

    public void updateModuleProgress(User user, Lesson lesson, int totalLessons) {


        UserModuleProgress progress = moduleProgressRepository
                .findByUserIdAndModuleId(user.getId(), lesson.getModule().getId())
                .orElseGet(() -> {
                    UserModuleProgress p = new UserModuleProgress();
                    p.setUser(user);
                    p.setModule(lesson.getModule());
                    p.setLessonsCompleted(0);
                    p.setTotalLessons(totalLessons);
                    p.setProgressPercent(0);
                    p.setCompleted(false);
                    return p;
                });


        if (Boolean.TRUE.equals(progress.getCompleted())) {
            return;
        }


        int updatedCompleted = Math.min(
                progress.getLessonsCompleted() + 1,
                totalLessons
        );
        progress.setLessonsCompleted(updatedCompleted);

        int percent = (int) ((updatedCompleted / (double) totalLessons) * 100);
        progress.setProgressPercent(percent);


        if (updatedCompleted >= totalLessons) {
            progress.setCompleted(true);
            progress.setProgressPercent(100);
        }


        moduleProgressRepository.save(progress);
    }
}