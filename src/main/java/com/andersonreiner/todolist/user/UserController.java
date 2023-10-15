package com.andersonreiner.todolist.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserController {
    
    //Essa anotação fica responsável por instanciar o objeto, cuidar do ciclo de vida.
    @Autowired
    private IUserRepository iuserRepository;

    @PostMapping("/")
    public UserModel create (@RequestBody UserModel userModel){
        var user = this.iuserRepository.findByUsername(userModel.getUsername());
        if(user != null){
            System.out.println("Usuário já existe!\n");
            return null;
        }
        var userCreated = this.iuserRepository.save(userModel);
        return userCreated;
    
    }
}
