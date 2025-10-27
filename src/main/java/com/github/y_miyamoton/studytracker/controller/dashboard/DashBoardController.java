package com.github.y_miyamoton.studytracker.controller.dashboard;

import com.github.y_miyamoton.studytracker.entity.SubjectMinuteEntity;
import com.github.y_miyamoton.studytracker.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Comparator;

@Controller
@RequiredArgsConstructor
public class DashBoardController {

    private final LogService logService;

    @GetMapping
    public String index(Model model) {
        var now = LocalDate.now();

        var todayStart = now.atStartOfDay();
        var todayEnd = now.plusDays(1).atStartOfDay();

        var wf = WeekFields.ISO;
        var weekStart = now.with(wf.dayOfWeek(), 1).atStartOfDay();
        var weekEnd = weekStart.plusDays(7);

        var monthStart = now.withDayOfMonth(1).atStartOfDay();
        var monthEnd = monthStart.plusMonths(1);

        var bySubject = logService.minutesBySubject(monthStart, monthEnd).stream()
                .sorted(Comparator.comparingInt(SubjectMinuteEntity::totalMinutes).reversed())
                .toList();
        var dashboardDTO = new DashboardDTO(
                logService.totalMinutes(todayStart, todayEnd),
                logService.totalMinutes(weekStart, weekEnd),
                logService.totalMinutes(monthStart, monthEnd),
                bySubject
        );

        model.addAttribute("dashboard", dashboardDTO);
        return "dashboard/index";
    }
}
