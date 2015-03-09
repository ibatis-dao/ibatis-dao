package fxapp01.dao;

import fxapp01.dto.IntRange;

/**
 * Интерфейс источника данных, поддерживающий их извлечение по-странично.
 * @author StarukhSA
 */
public interface IDataRangeFetcher {
    void fetch(IntRange aRowsRange, int pos);
}
