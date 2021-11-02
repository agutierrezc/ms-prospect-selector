package co.com.addi.infra.common;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimerUtil {

	public static void randomSleep() {
		Try.run(() -> Thread.sleep(getTime()))
				.onFailure(error -> log.error("[TimerUtil] Error trying to sleep the thread"));
	}

	private static long getTime() {
		long leftLimit = 1L;
		long rightLimit = 5000L;
		return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	}
}
