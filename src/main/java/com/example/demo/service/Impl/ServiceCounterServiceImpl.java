@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository repository;

    public ServiceCounterServiceImpl(ServiceCounterRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter counter) {
        return repository.save(counter);
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repository.findByIsActiveTrue();
    }
}
