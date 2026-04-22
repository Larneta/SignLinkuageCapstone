package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.dto.ModuleRequest;
import org.example.signlinkuagecapstone.dto.ModuleResponse;
import org.example.signlinkuagecapstone.dto.LessonResponse;
import org.example.signlinkuagecapstone.entity.Module;
import org.example.signlinkuagecapstone.entity.Lesson;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.entity.UserLessonProgress;
import org.example.signlinkuagecapstone.entity.UserModuleProgress;
import org.example.signlinkuagecapstone.repository.ModuleRepository;
import org.example.signlinkuagecapstone.repository.UserLessonProgressRepository;
import org.example.signlinkuagecapstone.repository.UserModuleProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final UserModuleProgressRepository userModuleProgressRepository;
    private final UserLessonProgressRepository userLessonProgressRepository;

    public ModuleService(
            ModuleRepository moduleRepository,
            UserModuleProgressRepository userModuleProgressRepository,
            UserLessonProgressRepository userLessonProgressRepository
    ) {
        this.moduleRepository = moduleRepository;
        this.userModuleProgressRepository = userModuleProgressRepository;
        this.userLessonProgressRepository = userLessonProgressRepository;
    }


    public Module createModule(ModuleRequest request) {
        Module module = new Module();
        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        module.setOrderIndex(request.getOrderIndex());
        return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public List<ModuleResponse> getAllModuleResponses(User user) {
        return moduleRepository.findAll()
                .stream()
                .map(module -> toModuleResponse(module, user))
                .toList();
    }

    public Module getModuleById(Long moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() ->
                        new RuntimeException("Module not found: " + moduleId));
    }

    public Module updateModule(Long moduleId, ModuleRequest request) {
        Module module = getModuleById(moduleId);
        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        module.setOrderIndex(request.getOrderIndex());
        return moduleRepository.save(module);
    }

    public void deleteModule(Long moduleId) {
        Module module = getModuleById(moduleId);
        moduleRepository.delete(module);
    }


    public ModuleResponse getModuleResponse(Long moduleId, User user) {
        Module module = getModuleById(moduleId);
        return toModuleResponse(module, user);
    }

    public ModuleResponse toModuleResponse(Module module) {
        return toModuleResponse(module, null);
    }


    public ModuleResponse toModuleResponse(Module module, User user) {

        UserModuleProgress progress = null;
        if (user != null) {
            progress = userModuleProgressRepository
                    .findByUserIdAndModuleId(user.getId(), module.getId())
                    .orElse(null);
        }

        List<LessonResponse> lessons = (module.getLessons() != null ? module.getLessons() : List.<Lesson>of())
                .stream()
                .map(lesson -> toLessonResponse(lesson, user))
                .collect(Collectors.toList());

        int totalLessons = lessons.size();
        int completedLessons = 0;
        if (progress != null) {
            completedLessons = progress.getLessonsCompleted() != null ? progress.getLessonsCompleted() : 0;
        }

        int progressPercent = totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;
        Boolean completed = progress != null ? progress.getCompleted() : false;

        return new ModuleResponse(
                module.getId(),
                module.getTitle(),
                module.getDescription(),
                totalLessons,
                completedLessons,
                progressPercent,
                completed,
                lessons
        );
    }

    private LessonResponse toLessonResponse(Lesson lesson, User user) {
        UserLessonProgress lessonProgress = null;
        if (user != null) {
            lessonProgress = userLessonProgressRepository
                    .findByUserIdAndLessonId(user.getId(), lesson.getId())
                    .orElse(null);
        }

        Boolean completed = lessonProgress != null ? lessonProgress.getCompleted() : false;

        return new LessonResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getVideoUrl(),
                lesson.getLessonOrder(),
                lesson.getDurationSeconds(),
                lesson.getModule().getId(),
                completed
        );
    }
}

