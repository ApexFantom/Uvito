//package com.Uvito.Uvito.repository;
//
//import com.Uvito.Uvito.models.Users;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//
//
//
//@Repository
//public class InMemoryUsersDAO {
//    private final List<Users> USERS = new ArrayList<>();
//
//
//    public List <Users> findAllUsers(){
//        return USERS;
//    }
//
//
//    public Users saveUsers(Users users){
//        USERS.add(users);
//        return users;
//    }
//
//
//    public Users findByEmail(String email){
//        return USERS.stream()
//                .filter(element -> element.getEmail().equals(email))
//                .findFirst()
//                .orElse(null);
//    }
//
//
//    public Users updateUsers(Users users){
//        var usersIndex = IntStream.range(0, USERS.size())
//                .filter(index -> USERS.get(index).getEmail().equals(users.getEmail()))
//                .findFirst()
//                .orElse(-1);
//            if(usersIndex > -1){
//                USERS.set(usersIndex, users);
//                return users;
//            }
//        return null;
//    }
//
//
//    public void deleteUsers(String email){
//        var user = findByEmail(email);
//        if (user != null){
//            USERS.remove(user);
//        }
//    }
//}
