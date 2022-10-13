package TCPServerClient;
public enum State {
    WAITING,
    ACTION_RESPONSE,//try to verify desicion
    GAMBLE_RESPONSE//asks if player wants to gamble if desiscion is dangerous 
}
