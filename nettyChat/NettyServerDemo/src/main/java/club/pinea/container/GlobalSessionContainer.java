package club.pinea.container;

import java.util.concurrent.ConcurrentHashMap;

import club.pinea.common.IOSession;

/**
 * session容器
 * @author pineapple
 *
 */
public class GlobalSessionContainer {
	private static ConcurrentHashMap<Integer, IOSession> clienterMap = new ConcurrentHashMap<Integer, IOSession>();

	public static void addIOSession(IOSession session) {
		clienterMap.put(session.getSessionId(), session);
	}

	public static int getIOSessionSize() {
		return clienterMap.size();
	}

	public static IOSession getIOSession(int sessionId) {
		return clienterMap.get(sessionId);
	}

	public static void delIOSession(IOSession session) {
		if (session != null) {
			clienterMap.remove(session.getSessionId());
		}
	}
}
