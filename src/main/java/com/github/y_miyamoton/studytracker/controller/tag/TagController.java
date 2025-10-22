package com.github.y_miyamoton.studytracker.controller.tag;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final UserContext userContext;

    @GetMapping
    public String list(Model model) {
        var tagList = tagService.findActive()
                .stream()
                .map(TagDTO::toDTO)
                .toList();
        model.addAttribute("tags", tagList);
        return "tags/list";
    }

    @GetMapping("/{tagId}")
    public String showDetail(@PathVariable("tagId") long tagId, Model model) {
        var tagDTO = tagService.findById(tagId)
                .map(TagDTO::toDTO)
                .orElseThrow(TagNotFoundException::new);
        model.addAttribute("tag", tagDTO);
        return  "tags/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TagForm form, Model model) {
        model.addAttribute("mode", "CREATE");
        return "tags/form";
    }

    @PostMapping
    public String create(@Validated TagForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }
        tagService.create(form.toEntity(userContext.currentUserId()));
        return "redirect:/tags";
    }

    @GetMapping("/{tagId}/editForm")
    public String showEditForm(@PathVariable("tagId") long tagId, Model model) {
        var form = tagService.findById(tagId)
                .map(TagForm::fromEntity)
                .orElseThrow(TagNotFoundException::new);
        model.addAttribute("tagForm", form);
        model.addAttribute("mode", "EDIT");
        return "tags/form";
    }

    @PutMapping("{tagId}")
    public String update(@PathVariable("tagId") long tagId, @Validated @ModelAttribute TagForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "EDIT");
            return "tags/form";
        }
        var entity = form.toEntity(tagId,userContext.currentUserId());
        tagService.update(entity);
        return "redirect:/tags/{tagId}";
    }

    @DeleteMapping("{tagId}")
    public String delete(@PathVariable("tagId") long tagId) {
        tagService.delete(tagId);
        return  "redirect:/tags";
    }
}
