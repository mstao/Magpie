/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mingshan.magpie;

/**
 * Asynchronous startup context
 *
 * @author mingshan
 */
public class Magpie {
    private static final MagpieDisruptor MAGPIE_DISRUPTOR = new MagpieDisruptor();
    private static AsyncReader asyncReader;

    /**
     * Inner class for lazy load.
     */
    private static class MagpieHolder {
        private static final Magpie INSTANCE = Magpie.create();
    }

    /**
     * Creates an instance of {@link Magpie}.
     *
     * @return the instance of {@link Magpie}
     */
    private static Magpie create() {
        return new Magpie();
    }

    /**
     * Returns the instance of {@link Magpie}.
     *
     * @return the instance of {@link Magpie}
     */
    private static Magpie getInstance() {
        return Magpie.MagpieHolder.INSTANCE;
    }

    /**
     * Start Disruptor.
     *
     * @return the Magpie
     */
    public static Magpie start() {
        MAGPIE_DISRUPTOR.start();
        return Magpie.getInstance();
    }

    /**
     * Stop Disruptor.
     */
    public static void stop() {
        MAGPIE_DISRUPTOR.stop();
    }

    /**
     * Records info via entity.
     *
     * @param message the entity of message
     * @param <E> the generics class
     */
    public <E> void read(E message) {
        checkDisruptorStatus();
        getAsyncReader().read(message);
    }

    /**
     * Records info via byte array.
     *
     * @param message the byte array of message
     */
    public void read(byte[] message) {
        checkDisruptorStatus();
        getAsyncReader().read(message);
    }

    /**
     * Checks the status of the Disruptor, if the Disruptor is not running,
     * throw RuntimeException.
     */
    private void checkDisruptorStatus() {
        if (MAGPIE_DISRUPTOR.getDisruptor() == null) {
            throw new RuntimeException("Disruptor is not run.");
        }
    }

    /**
     * Gets async reader.
     *
     * @return the reader
     */
    private static AsyncReader getAsyncReader() {
        if (asyncReader == null) {
            synchronized (AsyncReader.class) {
                if (asyncReader == null) {
                    asyncReader = new AsyncReader(MAGPIE_DISRUPTOR);
                }
            }
        }

        return asyncReader;
    }
}
