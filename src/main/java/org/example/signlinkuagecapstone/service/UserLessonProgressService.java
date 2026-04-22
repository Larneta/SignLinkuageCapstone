package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.entity.Lesson;
import org.example.signlinkuagecapstone.entity.UserLessonProgress;
import org.example.signlinkuagecapstone.repository.UserLessonProgressRepository;
import org.example.signlinkuagecapstone.service.LessonService;
import org.springframework.stereotype.Service;





@Service
public class UserLessonProgressService {

    private final UserLessonProgressRepository lessonProgressRepository;
    private final UserModuleProgressService moduleProgressService;
    private final LessonService lessonService;

    public UserLessonProgressService(
            UserLessonProgressRepository lessonProgressRepository,
            UserModuleProgressService moduleProgressService,
            LessonService lessonService
    ) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.moduleProgressService = moduleProgressService;
        this.lessonService = lessonService;
    }

    public void markLessonCompleted(User user, Long lessonId) {

        Lesson lesson = lessonService.getLessonById(lessonId);

        UserLessonProgress progress = lessonProgressRepository
                .findByUserIdAndLessonId(user.getId(), lessonId)
                .orElseGet(() -> {
                    UserLessonProgress p = new UserLessonProgress();
                    p.setUser(user);
                    p.setLesson(lesson);
                    return p;
                });

        // prevent double-counting
        if (Boolean.TRUE.equals(progress.getCompleted())) {
            return;
        }

        progress.setCompleted(true);
        progress.setProgressPercent(100);
        lessonProgressRepository.save(progress);

        int totalLessons =
                lessonService.getLessonsByModule(lesson.getModule().getId()).size();

        moduleProgressService.updateModuleProgress(user, lesson, totalLessons);
    }
}