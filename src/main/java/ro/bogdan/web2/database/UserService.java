package ro.bogdan.web2.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public void save(String email, String password) throws InvalidPassword {
        if (password.length() < 10) {
            throw new InvalidPassword("parola trebuie sa aibe peste 10 caractere");
        }

        if (userDAO.findByEmail(email).size() > 0) {
            throw new InvalidPassword("exista deja un utilizator cu acest email");
        }

        userDAO.save(email, password);
    }

    public List<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
