package com.example.test;

import com.example.test.entity.User;
import com.example.test.entity.Users;
import com.example.test.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@Controller
public class MainController {

    private final StorageService storageService;
    private final XmlConverter xmlConverter;
    private UsersContainer usersContainer;
    private String fileName;

    @Autowired
    public MainController(StorageService storageService, XmlConverter xmlConverter) {
        this.storageService = storageService;
        this.xmlConverter = xmlConverter;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        this.fileName = StringUtils.cleanPath(file.getOriginalFilename());
        this.usersContainer = storageService.storeFile(file, fileName);
        return "redirect:/users_list";
    }

    @GetMapping("/users_list")
    public String getListOfUsers(Model model) {
        List<User> usersList = usersContainer.getUserList();
        model.addAttribute("usersList", usersList);
        return "table";
    }

    @GetMapping("/add_user")
    public String showUserForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "addUser";
    }

    @PostMapping("/add_user")
    public String saveUser(@ModelAttribute("userForm") UserForm userForm){
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();

        if(firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            return "redirect:/add_user";
        }
        User user = new User(firstName, lastName);
        usersContainer.add(user);
        return "redirect:/users_list";
    }

    @PostMapping("/overwrite")
    public String overwriteFile() {
        Users users = new Users();
        users.setUsers(usersContainer.getUserList());
        xmlConverter.objectToXml(users, fileName);
        return "redirect:/users_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        usersContainer.getUserList().removeIf(user -> user.getId() == id);
        return "redirect:/users_list";
    }

    @PostMapping("/sortByFirstName")
    public String sortByFirstName() {
        usersContainer.getUserList().sort(Comparator.comparing(User::getFirstName));
        return "redirect:/users_list";
    }

    @PostMapping("/sortByLastName")
    public String sortByLastName() {
        usersContainer.getUserList().sort(Comparator.comparing(User::getLastName));
        return "redirect:/users_list";
    }

}
