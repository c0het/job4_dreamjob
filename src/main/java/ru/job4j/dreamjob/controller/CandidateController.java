package ru.job4j.dreamjob.controller;

import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateRepository;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.SimpleCandidateService;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("/create")
        public String getCreationPage() {
            return "candidates/create";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Кандидат не найден");
            return "errors/404";
        }
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Candidate candidate, Model model) {
        var isUpdate = candidateService.update(candidate);
        if (!isUpdate) {
            model.addAttribute("message", "Кандидат не найден");
            return "errors/404";
        }
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var idDeleted = candidateService.deleteById(id);
        if (idDeleted == null) {
            model.addAttribute("message", "Кандидат не найден");
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}
