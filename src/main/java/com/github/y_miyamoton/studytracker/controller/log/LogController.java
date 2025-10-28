package com.github.y_miyamoton.studytracker.controller.log;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.controller.subject.SubjectDTO;
import com.github.y_miyamoton.studytracker.entity.SubjectEntity;
import com.github.y_miyamoton.studytracker.service.LogService;
import com.github.y_miyamoton.studytracker.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final UserContext userContext;
    private final LogService logService;
    private final SubjectService subjectService;

    @GetMapping
    public String list(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @RequestParam(required = false) Long subjectId,

            Model model
    ){
        if (from == null) {
            from = LocalDate.now().minusDays(7);
        }
        if (to == null) {
            to = LocalDate.now();
        }
        var fromDateTime = from.atStartOfDay();
        var toDateTime = to.atTime(23, 59, 59);

        var subjectList = subjectService.findActive();
        var subjectMap = subjectList.stream()
                .collect(Collectors.toMap(
                        SubjectEntity::getSubjectId,
                        SubjectDTO::toDTO
                ));

        var logList = logService.findByPeriod(fromDateTime, toDateTime, subjectId)
                .stream()
                .map(logEntity -> {
                    var subject = subjectMap.get(logEntity.getSubjectId());
                    var subjectName = (subject != null) ? subject.name() : "(不明な科目)";
                    var colorCode  = (subject != null && subject.colorCode() != null) ? subject.colorCode() : "#e9ecef";
                    return LogDTO.toDTO(logEntity, subjectName, colorCode);
                })
                .toList();
        model.addAttribute("logs", logList);
        model.addAttribute("subjects", subjectList);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("subjectId", subjectId);

        return "logs/list";
    }

    @GetMapping("/{logId}")
    public String showDetail(@PathVariable("logId") long logId, Model model) {
        var logEntity = logService.findById(logId)
                .orElseThrow(LogNotFoundException::new);
        var logDTO = LogDTO.toDTO(logEntity);
        var form = LogForm.fromEntity(logEntity);
        var subjectList = subjectService.findActive()
                .stream()
                .map(SubjectDTO::toDTO)
                .toList();
        model.addAttribute("log",logDTO);
        model.addAttribute("logForm", form);
        model.addAttribute("logId", logId);
        model.addAttribute("subjects", subjectList);
        return "logs/edit";
    }

    @GetMapping("creationForm")
    public String showCreationForm(@ModelAttribute LogForm form, Model model) {
        var subjectList = subjectService.findActive()
                .stream()
                .map(SubjectDTO::toDTO)
                .toList();
        model.addAttribute("subjects", subjectList);
        return "logs/form";
    }

    @PostMapping
    public String create(@Validated LogForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }
        logService.create(form.toEntity(userContext.currentUserId()));
        return "redirect:/logs";
    }

    @PutMapping("/{logId}")
    public String update(
            @PathVariable("logId") long logId,
            @Validated @ModelAttribute LogForm form,
            BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()) {
            var subjectList = subjectService.findActive().stream()
                    .map(SubjectDTO::toDTO)
                    .toList();
            model.addAttribute("subjects", subjectList);
            model.addAttribute("logId", logId);
            return "logs/edit";
        }
        var entity = form.toEntity(logId, userContext.currentUserId());
        logService.update(entity);
        return "redirect:/logs";
    }

    @DeleteMapping("/{logId}")
    public String delete(@PathVariable("logId") long logId) {
        logService.delete(logId);
        return "redirect:/logs";
    }
}
