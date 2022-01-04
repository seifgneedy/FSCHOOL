package com.fschool.fschool;

import java.time.LocalDate;

import com.fschool.fschool.Model.Entities.User;
import com.fschool.fschool.Model.Services.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.*;

@Component
public class StartupConfiguration {
    @Autowired
	AdminService adminService;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        // Adding admin account if it doesn't exist
		User admin = new User();
		admin.setFirstName("Admin");
		admin.setLastName("Account");
		admin.setEmail("admin@admin.com");
		admin.setPassword("turkish coffee");
		admin.setRole("admin");
		admin.setSex("Male");
        admin.setBirthDate(LocalDate.now());
		adminService.addUser(admin);
    }
}
