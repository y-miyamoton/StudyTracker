package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final UserContext userContext;

    @GetMapping
    public String list(Model model) {
        var subjectList = subjectService.findActive()
                .stream()
                .map(SubjectDTO::toDTO)
                .toList();
        model.addAttribute("subjects", subjectList);
        return "subjects/list";
    }

    @GetMapping("/{id}")
    public  String showDetail(@PathVariable("id") long id, Model model) {
        var subjectEntity = subjectService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found."));
        model.addAttribute("subject", SubjectDTO.toDTO(subjectEntity) );
        return "subjects/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm () {
        return "subjects/subjectForm";
    }

    @PostMapping
    public String create(SubjectForm form) {
        subjectService.create(form.toEntity(userContext.currentUserId()));
        return "redirect:/subjects";
    }
}
