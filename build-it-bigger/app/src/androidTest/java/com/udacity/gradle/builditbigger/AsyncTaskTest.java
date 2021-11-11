package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class AsyncTaskTest{

    private Context mContext;
    @Test
    public void testCallJoke() {
        try {
            assertTrue(true);
            final CountDownLatch latch = new CountDownLatch(1);
            mContext = InstrumentationRegistry.getContext();
            EndpointsAsyncTask testTask = new EndpointsAsyncTask() {
                @Override
                protected void onPostExecute(String result) {
                    assertNotNull(result);
                    assertTrue(result.length() > 0);
                    latch.countDown();
                }
            };
            testTask.execute(mContext);
            latch.await();
        } catch (Exception e){
            fail("Get Exception Test - " + e.toString());
        }
    }
}
