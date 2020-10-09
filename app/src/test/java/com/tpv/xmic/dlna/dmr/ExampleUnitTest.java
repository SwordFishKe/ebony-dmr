package com.tpv.xmic.dlna.dmr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        PlayState state = PlayState.PAUSED;
        System.out.println(state.getCode());
    }

    public enum PlayState {
        PLAYING(1), PAUSED(2), STOPPED(3);
        int code;

        PlayState(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}