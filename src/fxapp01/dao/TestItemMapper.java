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

import fxapp01.dto.INestedRange;
import fxapp01.dto.SQLParams;
import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.TestItemDTO;
import java.io.IOException;

public interface TestItemMapper extends IDAOreadonly<TestItemDTO,Integer>, IDataWriter<TestItemDTO> {
    //List<TestItemDTO> select(INestedRange rowsrange) throws IOException;
    @Override
    List<TestItemDTO> select(SQLParams prm) throws IOException;
    TestItemDTO selectByPKey(BigInteger PKey) throws IOException;
    INestedRange<Integer> selectTotalRange() throws IOException;
    @Override
    int insertRow(TestItemDTO item) throws IOException;
    int insertRowBySP(TestItemDTO item) throws IOException;
    int insertRowBySP2(TestItemDTO item) throws IOException;
    @Override
    int updateRow(TestItemDTO item) throws IOException;
    @Override
    int deleteRow(TestItemDTO item) throws IOException;
}
