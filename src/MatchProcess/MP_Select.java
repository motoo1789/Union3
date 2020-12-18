package MatchProcess;

import java.util.Map;

public class MP_Select {
	private MP_Strategy mps;
	
	public MP_Select(MP_Strategy mps) {
		this.mps = mps;
	}
	public void s_match(Map map) {
		mps.match(map);
	}

}
