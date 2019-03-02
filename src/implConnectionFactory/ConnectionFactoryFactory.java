package implConnectionFactory;


import dbExceptions.DBSystemException;

public class ConnectionFactoryFactory {
    public enum FactoryType{RAW, DBCP, C3P0}

    private static FactoryType currentType = FactoryType.RAW;

    public static synchronized void setType(FactoryType type){
        currentType = type;
    }

    public synchronized static ConnectionFactory newConnectionFactory() {
        ConnectionFactory res = null;

        switch (currentType){
            case RAW:
                res = new ConnectionFactoryJdbc();
                break;
            case DBCP:
                res = new ConnectionFactoryDbcp();
                break;
            case C3P0:
                res = new ConnectionFactoryC3po();
                break;
            default:
                res = new ConnectionFactoryJdbc();
                System.out.println("Selected a RAW type by default.");
        }
        return res;
    }
}
