package basic.query;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyConnectionHandler implements InvocationHandler {

    private final Connection connection;
    private final QueryCounter counter;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ProxyConnectionHandler(Connection connection, QueryCounter counter) {
        this.connection = connection;
        this.counter = counter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (counter.isCountable()) {
            if (method.getName().equals("prepareStatement")) {
                return getConnectionWithCountQuery(method, args);
            }
        }
        return method.invoke(connection, args);
    }

    private Object getConnectionWithCountQuery(Method method, Object[] args)
        throws InvocationTargetException, IllegalAccessException {
        PreparedStatement preparedStatement = (PreparedStatement) method.invoke(connection, args);

        for (Object statement : args) {
            if (isQueryStatement(statement)) {
                log.info("### Query : {}", statement);
                counter.countOne();
                break;
            }
        }
        return preparedStatement;
    }

    private boolean isQueryStatement(Object statement) {
        if (statement.getClass().isAssignableFrom(String.class)) {
            String sql = (String) statement;
            return sql.startsWith("select");
        }
        return false;
    }
}