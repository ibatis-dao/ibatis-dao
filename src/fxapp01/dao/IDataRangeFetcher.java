package fxapp01.dao;

import fxapp01.dto.LimitedIntRange;

/**
 * Интерфейс источника данных, поддерживающий их извлечение по-странично.
 * @author StarukhSA
 */
public interface IDataRangeFetcher {
    void fetch(LimitedIntRange aRowsRange, int pos);
}
