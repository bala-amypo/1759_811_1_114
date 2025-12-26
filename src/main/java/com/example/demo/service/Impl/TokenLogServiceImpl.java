@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository repository;

    public TokenLogServiceImpl(TokenLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TokenLog> getLogsByToken(Long tokenId) {
        return repository.findByToken_Id(tokenId);
    }
}
