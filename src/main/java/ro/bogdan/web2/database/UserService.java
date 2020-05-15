package ro.bogdan.web2.database;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public void save(String email, String password) throws InvalidPassword {
        if (password.length() < 3) {
            throw new InvalidPassword("parola trebuie sa aibe peste 10 caractere");
        }

        if (userDAO.findByEmail(email).size() > 0) {
            throw new InvalidPassword("exista deja un utilizator cu acest email");
        }
        //folosim functia md5 pentru a 'cripta' parola
        String passwordMD5 = DigestUtils.md5Hex(password);
        userDAO.save(email, passwordMD5);
    }

    public List<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
