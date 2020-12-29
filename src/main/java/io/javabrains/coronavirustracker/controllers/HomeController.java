package io.javabrains.coronavirustracker.controllers;

import io.javabrains.coronavirustracker.model.LocationStatistics;
import io.javabrains.coronavirustracker.model.LocationStatistics;
import io.javabrains.coronavirustracker.services.CoronavirusDataService;
import io.javabrains.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStatistics> allStats = coronavirusDataService.getAllStatistics();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getTotalNewCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}