@Service
public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository repository;

    public QueueServiceImpl(QueuePositionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<QueuePosition> getQueue() {
        return repository.findAll();
    }
}
