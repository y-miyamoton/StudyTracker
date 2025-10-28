package com.github.y_miyamoton.studytracker.controller.timer;

import com.github.y_miyamoton.studytracker.config.UserContext;
import com.github.y_miyamoton.studytracker.controller.subject.SubjectDTO;
import com.github.y_miyamoton.studytracker.service.LogService;
import com.github.y_miyamoton.studytracker.service.SubjectService;
import com.github.y_miyamoton.studytracker.service.TimerService;
import jakarta.servlet.http.HttpSession;
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

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/timer")
@RequiredArgsConstructor
public class TimerController {

    private final SubjectService subjectService;
    private final LogService logService;
    private final TimerService timerService;
    private final UserContext userContext;

    private static final String S_BREAK_START_AT = "breakStartAt";
    private static final String S_BREAK_MINUTES  = "breakMinutes";
    private static final String S_FOCUS_MINUTES  = "focusMinutes";
    private static final String S_SUBJECT_ID     = "subjectId";

    @GetMapping
    public String index(@ModelAttribute TimerForm form, HttpSession session, Model model) {
        var subjectList = subjectService.findActive()
                .stream()
                .map(SubjectDTO::toDTO)
                .toList();
        model.addAttribute("subjects", subjectList);
        var active = timerService.currentActive();
        model.addAttribute("active", active);
        model.addAttribute("breakActive", false);
        var now = LocalDateTime.now();
        model.addAttribute("now", now);

        if (active != null) {
            long totalSec = active.getFocusMinutes() * 60L;
            long elapsedSec = Duration.between(active.getStartAt(), now).getSeconds();
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

        var breakStartAt = (LocalDateTime) session.getAttribute(S_BREAK_START_AT);
        var breakMinutes = (Integer)       session.getAttribute(S_BREAK_MINUTES);
        if (breakStartAt != null && breakMinutes != null && active == null) {
            long totalSec = breakMinutes * 60L;
            long elapsedSec = Math.max(0, Math.min(totalSec,
                    Duration.between(breakStartAt, now).getSeconds()));
            long remainingSec = totalSec - elapsedSec;
            int percent = totalSec == 0 ? 100 : (int) Math.round(elapsedSec * 100.0 / totalSec);
            var target = breakStartAt.plusMinutes(breakMinutes);

            model.addAttribute("breakActive", true);
            model.addAttribute("now", now);
            model.addAttribute("breakTotalSec", totalSec);
            model.addAttribute("breakElapsedSec", elapsedSec);
            model.addAttribute("breakRemainingSec", remainingSec);
            model.addAttribute("breakPercent", percent);
            model.addAttribute("breakTargetAt", target);
        }
        return "timer/index";
    }

    @PostMapping("/start")
    public String start(@ModelAttribute @Validated TimerForm form, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            var subjectList = subjectService.findActive()
                    .stream()
                    .map(SubjectDTO::toDTO)
                    .toList();
            model.addAttribute("subjects", subjectList);
            model.addAttribute("active", timerService.currentActive());
            return "timer/index";
        }
        clearBreakSession(session);

        session.setAttribute(S_SUBJECT_ID,    form.subjectId());
        session.setAttribute(S_FOCUS_MINUTES, form.focusMinutes());
        session.setAttribute(S_BREAK_MINUTES, form.breakMinutes());
        var entity = form.toEntity(userContext.currentUserId());
        timerService.startFocus(entity);
        redirectAttributes.addFlashAttribute("timerForm", form);
        return "redirect:/timer";
    }

    @PostMapping("/stop")
    public String stop(HttpSession session) {
        var active = timerService.currentActive();
        if (active != null) {
            var endAt = LocalDateTime.now();
            var timerDTO= TimerDTO.fromEntity(active);
            var newLog = timerDTO.toLogAt(endAt, "自動登録：ポモドーロタイマー");
            logService.create(newLog);
            timerService.stopFocus();
            clearBreakSession(session);
            return "redirect:/logs";
        }
        var breakStartAt = (LocalDateTime) session.getAttribute(S_BREAK_START_AT);
        if (breakStartAt != null) {
            clearBreakSession(session);
            return "redirect:/logs";
        }
        return "redirect:/timer";
    }

    @PostMapping("/break/start")
    public String startBreak(HttpSession session, RedirectAttributes redirectAttributes) {
        var active = timerService.currentActive();
        if (active == null) {
            return "redirect:/timer";
        }

        var endAt   = LocalDateTime.now();
        var timerDTO= TimerDTO.fromEntity(active);
        var newLog  = timerDTO.toLogAt(endAt, "自動登録：ポモドーロ（休憩開始）");
        logService.create(newLog);

        timerService.stopFocus();

        session.setAttribute(S_SUBJECT_ID,    active.getSubjectId());
        session.setAttribute(S_FOCUS_MINUTES, active.getFocusMinutes());
        session.setAttribute(S_BREAK_MINUTES, active.getBreakMinutes() != null ? active.getBreakMinutes() : 5);
        session.setAttribute(S_BREAK_START_AT, LocalDateTime.now());

        redirectAttributes.addFlashAttribute("timerForm",
                new TimerForm(active.getSubjectId(), active.getFocusMinutes(), active.getBreakMinutes()));

        return "redirect:/timer";
    }

    private void clearBreakSession(HttpSession session) {
        session.removeAttribute(S_BREAK_START_AT);
        session.removeAttribute(S_BREAK_MINUTES);
    }
}
