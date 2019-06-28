package cn.com.angke.appointment.mybatis.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MybatisMapper
 *
 * @author Chuck Lee
 */
public interface MybatisMapper {

    /**
     * Table列数据扫描
     *
     * @param table table
     * @return list
     */
    List<String> selectColumns(@Param("table") String table);

    /**
     * 动态插入
     *
     * @param table table
     * @param map   map
     */
    void insertSelective(@Param("table") String table, @Param("map") Map map);

    /**
     * 动态更新
     *
     * @param table table
     * @param ID    ID
     * @param map   map
     */
    void updateSelective(@Param("table") String table, @Param("ID") String ID, @Param("map") Map map);

    /**
     * Select One By field value
     *
     * @param table  table
     * @param fields fields
     * @return json
     */
    JSONObject selectOneBy(@Param("table") String table, @Param("fields") Object... fields);

    /**
     * Select One By field value
     *
     * @param table  table
     * @param fields fields
     * @return json
     */
    List<JSONObject> selectLstBy(@Param("table") String table, @Param("fields") Object... fields);
}