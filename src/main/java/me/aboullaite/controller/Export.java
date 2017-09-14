package me.aboullaite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.aboullaite.service.EmployeeService;
import me.aboullaite.view.PdfViewTest;


@Controller
public class Export {

    @Autowired
    EmployeeService userService;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public PdfViewTest download(final Model model) {
        model.addAttribute("users", userService.findAll());
        final PdfViewTest view = new PdfViewTest();
        return view;
    }


}
