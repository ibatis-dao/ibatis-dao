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
package fxapp01.dao;

/**
 *
 * @author serg
 */
public enum SqlDialect {
    Oracle("Oracle", "7"),
    PostgreSQL("PostgreSQL", "9");
    
    private final String dialect;
    private final String version;
            
    private SqlDialect(String dialect, String version) {
        this.dialect = dialect;
        this.version = version;
    }
    
    @Override
    public String toString(){
        return dialect;
    }

}
