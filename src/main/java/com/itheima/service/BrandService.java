package com.itheima.service;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();


    /**
     * 查询所有
     * @return
     */
    public List<Brand> selectAll(){
        //调用BrandMapper.selectAll()

        //2. 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. 调用方法
        List<Brand> brands = mapper.selectAll();

        sqlSession.close();

        return brands;
    }

    /**
     * 添加
     * @param brand
     */
    public void add(Brand brand){

        //2. 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. 调用方法
        mapper.add(brand);

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

    }



    /**
     * 根据id查询
     * @return
     */
    public Brand selectById(int id){
        //调用BrandMapper.selectAll()

        //2. 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. 调用方法
        Brand brand = mapper.selectById(id);

        sqlSession.close();

        return brand;
    }


    /**
     * 修改
     * @param brand
     */
    public void update(Brand brand){

        //2. 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. 调用方法
        mapper.update(brand);

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

    }
    public void deleteByIds(int[] ids){

        //2. 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4. 调用方法
        mapper.deleteByIds(ids);

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }
    public PageBean<Brand> selectByPage(int currentPage, int pageSize){
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        int begin=(currentPage-1)*pageSize;
        int size=pageSize;
        //4. 调用方法
        List<Brand> rows=mapper.selectByPage(begin,size);
        System.out.println("rows="+rows.toString());
        int totalCount=mapper.selectTotalCount();

        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //释放资源
        sqlSession.close();
        return pageBean;
    }
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize,Brand brand){
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        int begin=(currentPage-1)*pageSize;
        int size=pageSize;
        System.out.println("brand内容："+brand.toString());
        //4. 调用方法
        //处理brand条件,模糊表达式
        String brandName=brand.getBrandName();
        String companyName=brand.getCompanyName();

        if(brandName!=null){
            brandName=brandName.trim();
            if(brandName.length()==0){
                brandName=null;
                brand.setBrandName(brandName);
            }
        }
        if(companyName!=null){
            companyName=companyName.trim();
            if(companyName.length()==0){
                companyName=null;
                brand.setCompanyName(companyName);
            }
        }
        if(brand!=null){
            if(brandName!=null&&brandName.length()>0){
                brand.setBrandName("%"+brandName+"%");
            }
            if(companyName!=null&&companyName.length()>0){
                brand.setCompanyName("%"+companyName+"%");
            }
        }

        List<Brand> rows=mapper.selectByPageAndCondition(begin,size,brand);

        System.out.println("查到："+ rows.get(0).toString());
        Integer totalCount=mapper.selectTotalCountByCondition(brand);
        System.out.println("totalCount="+totalCount);
        PageBean<Brand> pageBean=new PageBean<>();

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //释放资源
        sqlSession.close();
        return pageBean;
    }

}
