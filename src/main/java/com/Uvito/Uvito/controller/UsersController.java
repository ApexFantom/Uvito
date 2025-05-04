package com.Uvito.Uvito.controller;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Uvito.Uvito.models.Users;
import com.Uvito.Uvito.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersController {


    private final UsersService service;

    @GetMapping
    public List<Users> findAllUsers(){
        return service.findAllUsers();
    }

@PostMapping("save_users")
public Users saveUsers(@RequestBody Users users) {

        return service.saveUsers(users);
}

@GetMapping("/{email}")
public Users findByEmail(@PathVariable String email){
        return service.findByEmail(email);

}
@PutMapping("update_users")
public Users updateUsers(@RequestBody Users users) {
        return service.updateUsers(users);
}

@DeleteMapping("delete_student/{email}")
public void deleteUsers(@PathVariable String email){
        service.deleteUsers(email);
}
}
