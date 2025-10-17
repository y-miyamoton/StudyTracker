package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/subjects")
    public String list(Model model) {
        var subjectList = subjectService.find()
                .stream()
                .map(SubjectDTO::toDTO)
                .toList();
        model.addAttribute("subjects", subjectList);
        return "subjects/list";
    }
}
