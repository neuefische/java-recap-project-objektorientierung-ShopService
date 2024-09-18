import java.util.UUID;

public class UUIDService implements IdService{
    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
