package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;



import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {




    @Test
    public void TakeVideo() {
        assertEquals(1, Main4Activity.VIDEO_CAPTURED);
    }

    @Test
    public void GetMessage()  {


        try {
            String message = "Server Disconnected.";
            assertEquals(true, Main4Activity.ClientThread.class.getMethod(message));
        } catch (NoSuchMethodException e) {
            e.printStackTrace( );
        }


    }}