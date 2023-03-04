package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.User;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    UsersService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @PostMapping
    public Result addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public Result editUser(@PathVariable Integer id, @RequestBody User user){
        return userService.editUser(id, user);
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }

}
