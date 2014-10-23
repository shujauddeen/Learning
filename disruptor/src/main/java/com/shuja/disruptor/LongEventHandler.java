package com.shuja.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>{

	public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		System.out.println("Event : " + event.get());
	}

}
