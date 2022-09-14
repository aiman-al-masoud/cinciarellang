package com.luxlunaris.cincia.backend.concurrency;

import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.CinciaCinciaClass;
import com.luxlunaris.cincia.backend.throwables.CinciaException;

public class Promise extends CinciaCinciaClass{

	protected Promise myself;
	protected CinciaObject result;
	protected CinciaException error;


	public boolean isSettled() {
		return isFullfilled() || isRejected();
	}

	public boolean isFullfilled() {
		return result!=null;
	}

	public boolean isRejected() {
		return error!=null;
	}

	public CinciaObject getResult() {
		return result;
	}

	public CinciaException getError() {
		return error;
	}



	public CinciaObject getResult(List<CinciaObject> args) {
		return result;
	}

	public CinciaException getError(List<CinciaObject> args) {
		return error;
	}

	public Promise() {
		myself = this;
		set("then", new CinciaMethod(this::then, this));
		set("getResult", new CinciaMethod(this::getResult, this));
		set("getError", new CinciaMethod(this::getError, this));
		set("await", new CinciaMethod(this::await, this));
	}



	public Promise(PureCinciaFunction promiseBody) {

		this();


		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					result = promiseBody.run( Arrays.asList() );	
				} catch (CinciaException e) {
					error = e;
				}

				synchronized(myself) {
					myself.notifyAll();	
				}

			}
		}).start();;

	}


	protected Promise(PureCinciaFunction resolve, PureCinciaFunction reject, Promise prevPromise) {


		this();

		new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (prevPromise) {
					while(!prevPromise.isSettled()) {
						try {
							prevPromise.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}


				try {

					if(prevPromise.isFullfilled()) {
						result = resolve.run( Arrays.asList(prevPromise.getResult()) );
					}

					if(prevPromise.isRejected()) {
						result = reject.run( Arrays.asList(prevPromise.getError()));
					}

				} catch (CinciaException e) {
					error = e;
				}


				synchronized(myself) {
					myself.notifyAll();	
				}


			}
		}).start();

	}


	public Promise then(PureCinciaFunction resolve, PureCinciaFunction reject) {
		return new Promise(resolve, reject, this);
	}


	public Promise then(List<CinciaObject> args) {
		return then((PureCinciaFunction)args.get(0), (PureCinciaFunction)args.get(1));
	}

	@Override
	public CinciaObject newInstance(List<CinciaObject> args) {

		PureCinciaFunction promiseBody = (PureCinciaFunction)args.get(0);		
		return new Promise(promiseBody);
	}


	public Promise await() {

		synchronized (this) {
			while(!isSettled()) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return this;
	}

	public Promise await(List<CinciaObject> args) {
		return await();
	}




}
