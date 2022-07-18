package com.ltp.gradesubmission;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {
    private List<Grade> studentGrades = new ArrayList<>();

    @GetMapping("/grades")
    public String getGrades(Model model) {
        model.addAttribute("grades", studentGrades);
        return "grades";
    }

    @GetMapping("/")
    public String gradeForm(Model model, @RequestParam(required = false) String id) {
        var gradeIndex = getGradeIndex(id);
        var grade = gradeIndex == Constants.NOT_FOUND ? new Grade() : studentGrades.get(gradeIndex);
        model.addAttribute("grade", grade);
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(@Valid Grade grade, BindingResult result) {
        if (result.hasErrors()) return "form";
        var gradeIndex = getGradeIndex(grade.getId());
        if (gradeIndex == Constants.NOT_FOUND) {
            studentGrades.add(grade);
        } else {
            studentGrades.set(gradeIndex, grade);
        }
        return "redirect:/grades";
    }

    private Integer getGradeIndex(String id) {
        for (var i = 0; i < studentGrades.size(); i++) {
            if (studentGrades.get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
}
