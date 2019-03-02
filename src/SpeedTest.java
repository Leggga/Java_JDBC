
import dao.User;
import dao.UserDao;
import dao.UserDaoJDBC;
import dbExceptions.DBSystemException;
import implConnectionFactory.ConnectionFactoryFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpeedTest {
    private static int COUNT = 100;

    public static void main(String[] args) throws DBSystemException {
        ConnectionFactoryFactory.setType(ConnectionFactoryFactory.FactoryType.RAW);

        //results
        //C3p0 - 1508
        //RAW - 6295
        //DBCP - 1451

        UserDao dao = new UserDaoJDBC();

        List<Long> timesList = new ArrayList<>();

        for (int k = 0; k < COUNT; k++){
            long start = System.nanoTime();

            List<User> usersList = dao.selectAll();
//            System.out.println(usersList);

            long end = System.nanoTime();

            long time = (end - start) / 1000;
            System.out.println(time);
            timesList.add(time);
        }

        Collections.sort(timesList);
        int count = 0;
        long sum = 0;
        for (int i = 20; i < timesList.size() - 20 ; i++) {
            sum += timesList.get(i);
            count++;
        }
        float avg = (float)sum / count;
        System.out.println("Average = " + avg);
    }
}
