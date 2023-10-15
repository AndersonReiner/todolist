package com.andersonreiner.todolist.user;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;


public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    //Cria metodo de busca passando o atributo e definido o retorno.
    UserModel findByUsername(String username);
}
