package com.codegym.controller;


import com.codegym.model.dto.RoleDto;
import com.codegym.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ModelAndView listRoles() {
        ModelAndView modelAndView = new ModelAndView("/role/list");
        Iterable<RoleDto> roleDtos = roleService.findAll();
        modelAndView.addObject("roleDtos", roleDtos);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/role/create");
        modelAndView.addObject("roleDto", new RoleDto());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveRole(@ModelAttribute("roleDto") RoleDto roleDto) {
        roleService.save(roleDto);
        ModelAndView modelAndView = new ModelAndView("/role/create");
        modelAndView.addObject("roleDto", new RoleDto());
        modelAndView.addObject("message", "New role created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<RoleDto> roleDto = roleService.findById(id);
        if (roleDto.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/role/edit");
            modelAndView.addObject("roleDto", roleDto.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateRole(@ModelAttribute("roleDto") RoleDto roleDto) {
        roleService.save(roleDto);
        ModelAndView modelAndView = new ModelAndView("/role/edit");
        modelAndView.addObject("roleDto", roleDto);
        modelAndView.addObject("message", "Role updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<RoleDto> roleDto = roleService.findById(id);
        if (roleDto.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/role/delete");
            modelAndView.addObject("roleDto", roleDto.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteRole(@ModelAttribute("roleDto") RoleDto roleDto) {
        roleService.remove(roleDto.getId());
        return "redirect:list";
    }
}
