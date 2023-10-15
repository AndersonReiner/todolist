package com.andersonreiner.todolist.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;


@RestController
@RequestMapping("user")
public class UserController {
    
    //Essa anotação fica responsável por instanciar o objeto, cuidar do ciclo de vida.
    @Autowired
    private IUserRepository iuserRepository;

    @PostMapping("/")
    public ResponseEntity create (@RequestBody UserModel userModel){
        var user = this.iuserRepository.findByUsername(userModel.getUsername());

        if(user != null){
            //retorna um status de erro e uma mensagem para a requisição.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        //recebe a senha do usuário e passa por uma criptografia antes de salvar, salva em um arryadeChar.
        var passwordHashred =  BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        var userCreated = this.iuserRepository.save(userModel);
        //retorna uma ststus http de usuário criado e o usuário.
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    
    }
}
