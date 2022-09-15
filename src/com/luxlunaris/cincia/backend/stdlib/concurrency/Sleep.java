package com.luxlunaris.cincia.backend.stdlib.concurrency;

import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;

public class Sleep extends CinciaFunction{

	public Sleep() {
		super(null, null);
	}

	@Override
	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {

		CinciaInt delayMillis = (CinciaInt) args.get(0);


		try {
			Thread.sleep(delayMillis.getValue());
		} catch (InterruptedException e) {
			throw new RuntimeException("Couldn't put current thread to sleep!");
		}

		return null;
	}

}
