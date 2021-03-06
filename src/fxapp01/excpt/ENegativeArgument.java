/*
 * Copyright 2015 serg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fxapp01.excpt;

/**
 *
 * @author StarukhSA
 */
public class ENegativeArgument extends IllegalArgumentException {
    public ENegativeArgument(String methodName) {
        super("Method "+methodName+"() arguments must be great than zero");
    }

    public ENegativeArgument(String methodName, String argName) {
        super("Method "+methodName+"("+argName+") argument must be great than zero");
    }    
}
