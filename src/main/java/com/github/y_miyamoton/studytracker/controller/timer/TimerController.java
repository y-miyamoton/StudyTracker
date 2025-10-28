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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        var active = timerService.currentActive();
        model.addAttribute("active", active);

        if (active != null) {
            var now = java.time.LocalDateTime.now();
            long totalSec = active.getFocusMinutes() * 60L;
            long elapsedSec = java.time.Duration.between(active.getStartAt(), now).getSeconds();
            if (elapsedSec < 0) elapsedSec = 0;
            if (elapsedSec > totalSec) elapsedSec = totalSec;

            long remainingSec = totalSec - elapsedSec;
            int percent = totalSec == 0 ? 100 : (int) Math.round(elapsedSec * 100.0 / totalSec);
            var target = active.getStartAt().plusMinutes(active.getFocusMinutes());

            model.addAttribute("totalSec", totalSec);
            model.addAttribute("elapsedSec", elapsedSec);
            model.addAttribute("remainingSec", remainingSec);
            model.addAttribute("percent", percent);
            model.addAttribute("targetAt", target);
        }
        return "timer/index";
    }

    @PostMapping("/start")
    public String start(@ModelAttribute @Validated TimerForm form, RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
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
        redirectAttributes.addFlashAttribute("timerForm", form);
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
