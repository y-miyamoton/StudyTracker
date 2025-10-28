package com.github.y_miyamoton.studytracker.controller.timer;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.controller.subject.SubjectDTO;
import com.github.y_miyamoton.studytracker.service.LogService;
import com.github.y_miyamoton.studytracker.service.SubjectService;
import com.github.y_miyamoton.studytracker.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/timer")
@RequiredArgsConstructor
public class TimerController {

    private final SubjectService subjectService;
    private final LogService logService;
    private final TimerService timerService;
    private final UserContext userContext;

    @GetMapping
    public String index(@ModelAttribute TimerForm form, Model model) {
        var subjectList = subjectService.findActive()
                .stream()
                .map(SubjectDTO::toDTO)
                .toList();
        model.addAttribute("subjects", subjectList);
        model.addAttribute("active", timerService.currentActive());
        return "timer/index";
    }

    @PostMapping("/start")
    public String start(@ModelAttribute @Validated TimerForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var subjectList = subjectService.findActive()
                    .stream()
                    .map(SubjectDTO::toDTO)
                    .toList();
            model.addAttribute("subjects", subjectList);
            model.addAttribute("active", timerService.currentActive());
            return "timer/index";
        }
        var entity = form.toEntity(userContext.currentUserId());
        timerService.startFocus(entity);
        return "redirect:/timer";
    }

    @PostMapping("/stop")
    public String stop() {
        var active = timerService.currentActive();
        if (active == null) {
            return "redirect:/timer";
        }
        var endAt = LocalDateTime.now();
        var timerDTO= TimerDTO.fromEntity(active);
        var newLog = timerDTO.toLogAt(endAt, "自動登録：ポモドーロタイマー");
        logService.create(newLog);
        timerService.stopFocus();
        return "redirect:/logs";
    }

    @PostMapping("/break/start")
    public String startBreak() {
        timerService.startBreak();
        return "redirect:/timer";
    }

    @PostMapping("/break/stop")
    public String stopBreak() {
        timerService.stopBreak();
        return "redirect:/timer";
    }


}
