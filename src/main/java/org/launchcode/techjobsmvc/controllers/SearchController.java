package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value = "search")
public class SearchController {

    static HashMap<String, String> columnChoices = new HashMap<>();

    public SearchController() {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");
    }

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs;
        if (searchType.equals("all") && (searchTerm == null || searchTerm.isEmpty())) {
            jobs = JobData.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Search Results");
        return "search";
    }
}
