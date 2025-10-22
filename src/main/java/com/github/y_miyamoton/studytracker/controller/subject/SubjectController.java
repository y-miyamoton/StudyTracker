package com.github.y_miyamoton.studytracker.controller.subject;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{subjectId}")
    public  String showDetail(@PathVariable("subjectId") long subjectId, Model model) {
        var subjectDTO = subjectService.findById(subjectId)
                .map(SubjectDTO::toDTO)
                .orElseThrow(SubjectNotFoundException::new);
        model.addAttribute("subject", subjectDTO);
        return "subjects/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm (@ModelAttribute SubjectForm form, Model model) {
        model.addAttribute("mode", "CREATE");
        return "subjects/form";
    }

    @PostMapping
    public String create(@Validated SubjectForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }
        subjectService.create(form.toEntity(userContext.currentUserId()));
        return "redirect:/subjects";
    }

    @GetMapping("/{subjectId}/editForm")
    public String showEditForm(@PathVariable("subjectId") long subjectId, Model model){
        var form = subjectService.findById(subjectId)
                .map(SubjectForm::fromEntity)
                        .orElseThrow(SubjectNotFoundException::new);
        model.addAttribute("subjectForm", form);
        model.addAttribute("mode", "EDIT");
        return "subjects/form";
    }

    @PutMapping("{subjectId}")
    public String update(@PathVariable("subjectId") long subjectId, @Validated @ModelAttribute SubjectForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("mode", "EDIT");
            return "subjects/form";
        }
        var entity = form.toEntity(subjectId,userContext.currentUserId());
        subjectService.update(entity);
        return "redirect:/subjects/{subjectId}";
    }

    @DeleteMapping("{subjectId}")
    public String delete(@PathVariable("subjectId") long subjectId){
        subjectService.archive(subjectId);
        return "redirect:/subjects";
    }
}
