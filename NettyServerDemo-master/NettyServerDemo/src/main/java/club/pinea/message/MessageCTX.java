package club.pinea.message;

import club.pinea.common.IOSession;

public class MessageCTX {
	private Object messasge;
	private IOSession session;

	public MessageCTX(IOSession session, Object messasge) {
		this.session = session;
		this.messasge = messasge;
	}

	public Object getMessasge() {
		return messasge;
	}

	public void setMessasge(Object messasge) {
		this.messasge = messasge;
	}

	public IOSession getSession() {
		return session;
	}

	public void setSession(IOSession session) {
		this.session = session;
	}
}