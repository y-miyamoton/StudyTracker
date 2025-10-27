package com.github.y_miyamoton.studytracker.controller.dashboard;

import com.github.y_miyamoton.studytracker.entity.SubjectMinuteEntity;
import com.github.y_miyamoton.studytracker.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Comparator;

@Controller
@RequiredArgsConstructor
public class DashBoardController {

    private final LogService logService;

    @GetMapping
    public String index(@RequestParam(name = "range", defaultValue = "month") String range, Model model) {
        var now = LocalDate.now();

        var todayStart = now.atStartOfDay();
        var todayEnd = now.plusDays(1).atStartOfDay();

        var wf = WeekFields.ISO;
        var weekStart = now.with(wf.dayOfWeek(), 1).atStartOfDay();
        var weekEnd = weekStart.plusDays(7);

        var monthStart = now.withDayOfMonth(1).atStartOfDay();
        var monthEnd = monthStart.plusMonths(1);

        var dashboardDTO = new DashboardDTO(
                logService.totalMinutes(todayStart, todayEnd),
                logService.totalMinutes(weekStart, weekEnd),
                logService.totalMinutes(monthStart, monthEnd)
        );

        var byDay = logService.minutesBySubject(todayStart, todayEnd).stream()
                .sorted(Comparator.comparingInt(SubjectMinuteEntity::totalMinutes).reversed())
                .toList();
        var byWeek = logService.minutesBySubject(weekStart, weekEnd).stream()
                .sorted(Comparator.comparingInt(SubjectMinuteEntity::totalMinutes).reversed())
                .toList();
        var byMonth = logService.minutesBySubject(monthStart, monthEnd).stream()
                .sorted(Comparator.comparingInt(SubjectMinuteEntity::totalMinutes).reversed())
                .toList();
        var minutesDTO = new MinutesDTO(byDay, byWeek, byMonth);
        model.addAttribute("dashboard", dashboardDTO);
        model.addAttribute("minutes", minutesDTO);
        model.addAttribute("range", range);
        model.addAttribute("today", todayStart);
        model.addAttribute("weekFrom", weekStart);
        model.addAttribute("weekTo", weekEnd);
        model.addAttribute("monthFrom", monthStart);
        model.addAttribute("monthTo", monthEnd);
        return "dashboard/index";
    }
}
