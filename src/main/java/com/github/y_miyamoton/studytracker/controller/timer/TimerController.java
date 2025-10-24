package com.github.y_miyamoton.studytracker.controller.timer;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.controller.subject.SubjectDTO;
import com.github.y_miyamoton.studytracker.service.SubjectService;
import com.github.y_miyamoton.studytracker.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/timer")
@RequiredArgsConstructor
public class TimerController {

    private final SubjectService subjectService;
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
    public String start(@Validated TimerForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var subjectList = subjectService.findActive()
                    .stream()
                    .map(SubjectDTO::toDTO)
                    .toList();
            model.addAttribute("subjects", subjectList);
            model.addAttribute("active", timerService.currentActive());
            return "redirect:/timer";
        }
        var entity = form.toEntity(userContext.currentUserId());
        timerService.startFocus(entity);
        return "redirect:/timer";
    }

    @PostMapping("/stop")
    public String stop(@ModelAttribute @Validated TimerForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("active", timerService.currentActive());
            return "redirect:/timer";
        }
        timerService.stopFocus();
        return "redirect:/timer";
    }

    @PostMapping("/break/start")
    public String startBreak(@ModelAttribute @Validated TimerForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("active", timerService.currentActive());
            return "redirect:/timer";
        }
        timerService.startBreak();
        return "redirect:/timer";
    }

    @PostMapping("/break/stop")
    public String stopBreak(@ModelAttribute @Validated TimerForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("active", timerService.currentActive());
            return "redirect:/timer";
        }
        timerService.stopBreak();
        return "redirect:/timer";
    }


}
