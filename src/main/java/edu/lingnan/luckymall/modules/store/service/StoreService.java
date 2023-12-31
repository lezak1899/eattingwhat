package edu.lingnan.luckymall.modules.store.service;

import edu.lingnan.luckymall.modules.store.entity.Store;

import java.util.List;

/**
 * (Store)表服务接口
 *
 * @author makejava
 * @since 2020-06-14 11:02:46
 */
public interface StoreService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Store queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Store> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param store 实例对象
     * @return 实例对象
     */
    Store insert(Store store);

    /**
     * 修改数据
     *
     * @param store 实例对象
     * @return 实例对象
     */
    Store update(Store store);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询多条数据
     *
     * @return 所有对象列表
     */
    List<Store> queryAll(Store store);

    List<Store> selectByNameLike(String storeNameStr);


}