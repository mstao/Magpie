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
package me.mingshan.magpie.demo.web.export;

import me.mingshan.logger.async.api.LogExport;
import me.mingshan.logger.async.api.Message;
import me.mingshan.logger.async.serialize.Serializer;
import me.mingshan.logger.async.serialize.SerializerHolder;

public class ConsoleLogExportImpl implements LogExport {

    @Override
    public void export(byte[] message) {
        Serializer serializer = SerializerHolder.serializerImpl();
        System.out.println("zz = " + message);
        System.out.println("zz = " + serializer.readObject(message, Message.class));
    }

}
