package org.example.signlinkuagecapstone.service;

import org.example.signlinkuagecapstone.dto.LessonCreateRequest;
import org.example.signlinkuagecapstone.dto.LessonResponse;
import org.example.signlinkuagecapstone.entity.Lesson;
import org.example.signlinkuagecapstone.entity.Module;
import org.example.signlinkuagecapstone.repository.LessonsRepository;
import org.example.signlinkuagecapstone.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonsRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public LessonService(LessonsRepository lessonRepository,
                         ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    public Lesson createLesson(LessonCreateRequest request) {

        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() ->
                        new RuntimeException("Module not found: " + request.getModuleId())
                );

        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setLessonOrder(request.getLessonOrder());
        lesson.setDurationSeconds(request.getDurationSeconds());
        lesson.setModule(module);

        return lessonRepository.save(lesson);
    }

    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException("Lesson not found: " + lessonId)
                );
    }

    public List<Lesson> getLessonsByModule(Long moduleId) {
        return lessonRepository.findByModuleId(moduleId);
    }

    public Lesson updateLesson(Long lessonId, LessonCreateRequest request) {

        Lesson lesson = getLessonById(lessonId);

        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() ->
                        new RuntimeException("Module not found: " + request.getModuleId())
                );

        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setLessonOrder(request.getLessonOrder());
        lesson.setDurationSeconds(request.getDurationSeconds());
        lesson.setModule(module);

        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long lessonId) {
        Lesson lesson = getLessonById(lessonId);
        lessonRepository.delete(lesson);
    }

    public LessonResponse toLessonResponse(Lesson lesson, Boolean completed) {
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
