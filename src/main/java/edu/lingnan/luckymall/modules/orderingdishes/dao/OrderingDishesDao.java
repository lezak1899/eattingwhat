package edu.lingnan.luckymall.modules.orderingdishes.dao;

import edu.lingnan.luckymall.modules.orderingdishes.entity.OrderingDishes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (OrderingDishes)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-14 11:02:46
 */
@Mapper
@Repository
public interface OrderingDishesDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderingDishes queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OrderingDishes> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderingDishes 实例对象
     * @return 对象列表
     */
    List<OrderingDishes> queryAll(OrderingDishes orderingDishes);

    /**
     * 新增数据
     *
     * @param orderingDishes 实例对象
     * @return 影响行数
     */
    int insert(OrderingDishes orderingDishes);

    /**
     * 修改数据
     *
     * @param orderingDishes 实例对象
     * @return 影响行数
     */
    int update(OrderingDishes orderingDishes);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}