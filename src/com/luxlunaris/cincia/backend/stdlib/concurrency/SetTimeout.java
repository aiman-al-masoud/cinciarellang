package com.luxlunaris.cincia.backend.stdlib.concurrency;

import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;

public class SetTimeout extends CinciaFunction {

	public SetTimeout() {
		super(null, null);
	}

	@Override
	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {

		CinciaFunction runnable = (CinciaFunction) args.get(0);
		CinciaInt delayMillis = (CinciaInt) args.get(1);

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(delayMillis.toJava());
					runnable.run(args, enviro);
				} catch (InterruptedException e) {
					throw new RuntimeException("Couldn't start timeout!");
				}

			}
		}).start();;

		return null;

	}

}
