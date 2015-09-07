package api.ret.obj;

public class LoginInfo extends RetObjBase {
	private long uid = -1;
	private String token = null;
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LoginInfo(long uid, String token) {
		super();
		this.uid = uid;
		this.token = token;
	}
	public LoginInfo() {
		super();
	}
}
