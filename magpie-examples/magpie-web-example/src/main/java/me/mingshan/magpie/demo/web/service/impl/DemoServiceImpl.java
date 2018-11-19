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
package me.mingshan.magpie.demo.web.service.impl;

import me.mingshan.magpie.demo.web.service.DemoService;
import me.mingshan.magpie.source.c1.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * @author mingshan
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Log
    public String test(String name) {
        throw new IllegalArgumentException("参数有问题");
        //return "hello" + name;
    }
}
