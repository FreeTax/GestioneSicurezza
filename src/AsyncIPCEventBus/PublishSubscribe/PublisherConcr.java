package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class PublisherConcr implements Publisher{
    @Override
    public void publish(Message message, EventBusService service) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                service.addMessage(message);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        //service.addMessage(message);
    }
}
