//package com.Uvito.Uvito.Impl;
//
//
//import com.Uvito.Uvito.repository.InMemoryUsersDAO;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import com.Uvito.Uvito.models.Users;
//import com.Uvito.Uvito.service.UsersService;
//
//
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class inMemoryUsersServiceImpl implements UsersService {
//    private final InMemoryUsersDAO repository;
//
//    @Override
//    public List <Users> findAllUsers(){
//        return repository.findAllUsers();
//    }
//
//    @Override
//    public Users saveUsers(Users users){
//        return repository.saveUsers(users);
//    }
//
//    @Override
//    public Users findByEmail(String email){
//        return repository.findByEmail(email);
//    }
//
//    @Override
//    public Users updateUsers(Users users){
//        return repository.updateUsers(users);
//    }
//
//    @Override
//    public void deleteUsers(String email){
//        repository.deleteUsers(email);
//    }
//
//
//}
