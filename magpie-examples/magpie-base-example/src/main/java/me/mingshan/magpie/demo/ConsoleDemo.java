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
package me.mingshan.magpie.demo;

import me.mingshan.magpie.AsyncReader;
import me.mingshan.magpie.Magpie;
import me.mingshan.magpie.api.Message;

public class ConsoleDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setServiceName("aa " + i);
            Magpie.start().read(message);
        }
        Magpie.stop();
    }
}
