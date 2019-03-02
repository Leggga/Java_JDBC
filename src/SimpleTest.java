import dao.User;
import dao.UserDao;
import dao.UserDaoJDBC;
import dbExceptions.DBSystemException;
import dbExceptions.MyDaoException;

import java.util.List;

public class SimpleTest {
    public static void main(String[] args) throws MyDaoException {
        UserDao daoTest = new UserDaoJDBC();

        try {
            List<User> users = daoTest.selectAll();

            for(User person : users){
                System.out.println(person.getId() + "\t" + person.getEmail() + "\t" + person.getLogin());
            }

            for (int i = 0; i < 20; i++) {
                daoTest.insert(new User(i,"Person@gmail" + i,"login","pass"));
            }
//            User user = new User(89,"hot@mail.com","SuperMan","xxx");
//            User user2 = new User(90,"hot@mail.com","SuperMan","xxx");
//            System.out.println("Number of the primary key:" + daoTest.insert(user));

//            daoTest.deleteById();
        } catch (DBSystemException e) {
            e.printStackTrace();
        }
    }
}
