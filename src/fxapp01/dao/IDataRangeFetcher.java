package fxapp01.dao;

import fxapp01.dto.INestedRange;

/**
 * Интерфейс источника данных, поддерживающий их извлечение по-странично.
 * @author StarukhSA
 */
public interface IDataRangeFetcher {
    void fetch(INestedRange aRowsRange, int pos);
}
