package cn.com.angke.appointment.mybatis.mapper;

import com.alibaba.fastjson.JSONObject;
import com.somenew.utils.StringOpt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MybatisWrapper
 *
 * @author Chuck Lee
 */
@Component
public class MybatisWrapper implements MybatisMapper {

    private static final Log LOGGER = LogFactory.getLog(MybatisWrapper.class);

    /**
     * Table column 应用内 ConcurrentHashMap 缓存
     */
    private static final Map<String, List<String>> TABLE_COLUMN_CACHE_MAP
            = new ConcurrentHashMap<>(64);

    @Autowired
    private MybatisMapper mybatisMapper;

    @Override
    public void insertSelective(String table, Map map) {
        mybatisMapper.insertSelective(table, map);
    }

    @Override
    public void updateSelective(String table, String ID, Map map) {
        mybatisMapper.updateSelective(table, ID, map);
    }

    @Override
    public JSONObject selectOneBy(String table, Object... fields) {
        return mybatisMapper.selectOneBy(table, fields);
    }

    @Override
    public List<JSONObject> selectLstBy(String table, Object... fields) {
        return mybatisMapper.selectLstBy(table, fields);
    }

    /**
     * 动态新增或修改表数据
     *
     * @param tableName tableName
     * @param map       map
     * @return map
     */
    public Map<String, Object> save(final String tableName, final Map<String, Object> map) {
        Map<String, Object> tableData = this.prepare(tableName, map);

        String ID = (String) tableData.get("ID");
        if (StringUtils.isEmpty(ID)) {
            ID = StringOpt.produceUUID();
            tableData.put("ID", ID);
            mybatisMapper.insertSelective(tableName, tableData);
            map.put("ID", ID);
            return map;
        }

        mybatisMapper.updateSelective(tableName, ID, tableData);
        return map;
    }


    /**
     * Column预处理并返回真正的insert数据或update数据+
     *
     * @param tableName 表名
     * @param map       原始数据
     * @return tableData 真正的insert数据或update数据
     */
    public final Map<String, Object> prepare(final String tableName, final Map<String, Object> map) {
        final List<String> columnNames = this.selectColumns(tableName);
        final Map<String, Object> tableData = new HashMap(map);
        checkColumns(columnNames, tableData);
        return tableData;
    }

    /**
     * 数据库Table查询Column
     *
     * @param table table
     * @return list
     */
    public final List<String> selectColumns(final String table) {
        List<String> columnNames = TABLE_COLUMN_CACHE_MAP.get(table);
        if (CollectionUtils.isEmpty(columnNames)) {
            columnNames = mybatisMapper.selectColumns(table);
            if (CollectionUtils.isEmpty(columnNames)) {
                LOGGER.error(table + ":No column found error...");
                throw new RuntimeException("系统异常");
            }
            TABLE_COLUMN_CACHE_MAP.put(table, columnNames);
        }
        return columnNames;
    }

    /**
     * 表字段检查，不属于表字段的Key自动剔除
     *
     * @param columns columns
     * @param map     map
     */
    private static void checkColumns(final List<String> columns, final Map<String, Object> map) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            String column = e.getKey();
            if (!columns.contains(column)) {
                it.remove();
            }
        }
    }
}
