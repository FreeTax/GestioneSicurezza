package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;

public class PublisherConcr implements Publisher{
    @Override
    public void publish(Message message, EventBusService service) throws SQLException {
        service.addMessage(message);
    }
}
