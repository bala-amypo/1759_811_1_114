@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository logRepository;
    private final QueuePositionRepository queueRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogRepository logRepository,
                            QueuePositionRepository queueRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logRepository = logRepository;
        this.queueRepository = queueRepository;
    }

    @Override
    public Token issueToken(Long counterId) {
        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!counter.getIsActive()) {
            throw new IllegalArgumentException("Counter not active");
        }

        int position = tokenRepository
                .findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING")
                .size() + 1;

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setTokenNumber(counter.getCounterName() + "-" + position);

        Token saved = tokenRepository.save(token);

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(position);
        queueRepository.save(qp);

        TokenLog log = new TokenLog();
        log.setToken(saved);
        log.setMessage("Token issued");
        logRepository.save(log);

        return saved;
    }

    @Override
    public Token updateStatus(Long tokenId, String newStatus) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String current = token.getStatus();

        boolean valid =
                (current.equals("WAITING") && newStatus.equals("SERVING")) ||
                (current.equals("SERVING") && newStatus.equals("COMPLETED")) ||
                newStatus.equals("CANCELLED");

        if (!valid || (current.equals("WAITING") && newStatus.equals("COMPLETED"))) {
            throw new IllegalArgumentException("Invalid status transition");
        }

        token.setStatus(newStatus);

        if (newStatus.equals("COMPLETED") || newStatus.equals("CANCELLED")) {
            token.setCompletedAt(LocalDateTime.now());
        }

        Token saved = tokenRepository.save(token);

        TokenLog log = new TokenLog();
        log.setToken(saved);
        log.setMessage("Status changed to " + newStatus);
        logRepository.save(log);

        return saved;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
