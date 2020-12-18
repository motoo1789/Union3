package MatchProcess;

import java.util.HashMap;
import java.util.Map;

import test.Pattern_IO;

// �p�^�[���G���[�����m����
public class MatchProcess {

	Map<String,MP_Strategy> mps = new HashMap<>();
	public MatchProcess() {

		mps.put("Factory Method", new MP_FactoryMethod());
		mps.put("Adapter", new MP_Adapter());
		mps.put("Composite", new MP_Composite());

	}

	public void process(Map map) {

		Pattern_IO pio = new Pattern_IO();
		MP_Select mp = new MP_Select(mps.get(pio.p_read()));
		mp.s_match(map);

	}

}
