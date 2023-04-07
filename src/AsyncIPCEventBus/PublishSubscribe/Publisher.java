package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;

public interface  Publisher {

    void publish(Message message, EventBusService service) throws SQLException;
}
