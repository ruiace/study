package com.rp.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.rp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    void aRtest5(){
        User user = new User();
        user.setId(1199110934904213506L);
        user.setEmail("123456789");
        boolean insert = user.updateById();
        //userMapper.insert()
        System.out.println("insert--->" + insert);
    }

    @Test
    void aRtest4(){
        User user = new User();
        boolean insert = user.deleteById(1199110004569804802L);
        System.out.println("insert--->" + insert);
    }


    @Test
    void aRtest3(){
        User user = new User();
        user.setId(1199110154633711617L);
        boolean insert = user.deleteById();
        System.out.println("insert--->" + insert);
    }

    @Test
    void aRtest2(){
        User user = new User();
        user.setUserName("夏天6");
        user.setAge(35);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setId(1199110154633711617L);
        user.setDesc("这是一个描述信息");
        boolean insert = user.insertOrUpdate();
        System.out.println("insert--->" + insert);
    }

    @Test
    void aRtest1(){
        User user = new User();
        user.setUserName("夏天1");
        user.setAge(35);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setDesc("这是一个描述信息");
        boolean insert = user.insert();
        System.out.println("insert--->" + insert);
    }



    @Test
    void deleteWrapper4(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        userLambdaQueryWrapper.like(User::getUserName,"芮朋");
        int i = userMapper.delete(userLambdaQueryWrapper);
        System.out.println("影响记录数="+i);
    }
    @Test
    void deleteWrapper3(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","冬天");
        int i = userMapper.deleteByMap(map);
        System.out.println("影响记录数="+i);
    }


    @Test
    void deleteWrapper2(){
        int i = userMapper.deleteBatchIds(Arrays.asList(1198273789604782081L));
        System.out.println("影响记录数="+i);
    }

    @Test
    void deleteWrapper1(){
        int i = userMapper.deleteById(1198276070102376449L);
        System.out.println("影响记录数="+i);
    }



    @Test
    void updateWrapper8(){

        boolean update = new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getId, 1198268222358159362L).set(User::getEmail, "54321").update();
        System.out.println("影响记录数="+update);
    }

    @Test
    void updateWrapper7(){

        LambdaUpdateWrapper<User> lambda = new UpdateWrapper().lambda();
        lambda.eq(User::getId,1198268222358159362L).set(User::getEmail,"1234444444");
        //wrapper.eq("id",1198268222358159362L).set("email","123456789").set("age",21);
        int update = userMapper.update(null,lambda);
        System.out.println("影响记录数="+update);
    }

    @Test
    void updateWrapper6(){

        UpdateWrapper<User> wrapper = new UpdateWrapper();
        wrapper.eq("id",1198268222358159362L).set("email","123456789").set("age",21);
        int update = userMapper.update(null,wrapper);
        System.out.println("影响记录数="+update);
    }


    @Test
    void updateWrapper5(){


        User whereUser = new User();
        whereUser.setId(1198268222358159362L);
        UpdateWrapper<User> wrapper = new UpdateWrapper(whereUser);
        wrapper.set("email","123456").set("age",20);
        int update = userMapper.update(null,wrapper);
        System.out.println("影响记录数="+update);
    }

    @Test
    void updateWrapper3(){

        User user = new User();
        //user.setId(1198268222358159362L);
        user.setAge(34);
        user.setEmail("ruipeng123@126.com");
        User whereUser = new User();
        whereUser.setId(1198268222358159362L);
        UpdateWrapper<User> wrapper = new UpdateWrapper(whereUser);
        //wrapper.eq("id",1198268222358159362L);
        int update = userMapper.update(user,wrapper);
        System.out.println("影响记录数="+update);
    }


    @Test
    void updateWrapper2(){

        User user = new User();
        //user.setId(1198268222358159362L);
        user.setAge(33);
        user.setEmail("ruipeng@126.com");
        UpdateWrapper<User> wrapper = new UpdateWrapper();
        wrapper.eq("id",1198268222358159362L);
        int update = userMapper.update(user,wrapper);
        System.out.println("影响记录数="+update);
    }

    @Test
    void updateWrapper1(){

        User user = new User();
        user.setId(1198268222358159362L);
        user.setAge(32);
        int update = userMapper.updateById(user);
        System.out.println("影响记录数="+update);
    }

    @Test
    void selectWrapper25(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        Page<User> page = new Page<>(1,2);
        IPage<User> users = userMapper.selectMyPage(page,qw);
        users.getRecords().forEach(System.out::println);
    }

    @Test
    void selectWrapper24() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","id").lt("age", 40);

        Page<User> page = new Page(1,2,false);

        IPage<Map<String,Object>> userIPage = userMapper.selectMapsPage(page,queryWrapper);

        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getTotal());
        System.out.println(userIPage.getRecords());

    }


    @Test
    void selectWrapper23() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("age", 40);

        Page<User> page = new Page(1,2,false);

        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);

        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getTotal());
        // System.out.println(userIPage.getRecords());

    }


    @Test
    void selectWrapper22() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("age", 40);

        Page<User> page = new Page(1,2);

        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);

        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getTotal());
       // System.out.println(userIPage.getRecords());

    }


    @Test
    void selectWrapper21() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "王").lt("age", 40);
        List<User> users = userMapper.selectAll(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper20() {


        //() -> LambdaQueryChainWrapper
        //List<User> users = new LambdaQueryChainWrapper<User>(userMapper).select(User::getEmail,User::getUserName).like(User::getUserName, "雨").lt(User::getAge, 40).list();
        List<User> users = new LambdaQueryChainWrapper<User>(userMapper).select(User::getEmail,User::getUserName).like(User::getUserName, "雨").lt(User::getAge, 40).list();
        //LambdaQueryWrapper<User> lambda = Wrappers.lambdaQuery();
        //lambda.like(User::getUserName,"雨").lt(User::getAge,40);

        //List<User> users = userMapper.selectList(lambda);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper19() {
        //LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        //LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambda = Wrappers.lambdaQuery();
        lambda.like(User::getUserName,"雨").lt(User::getAge,40);

        List<User> users = userMapper.selectList(lambda);
        users.forEach(System.out::println);
    }


    /**
     * 11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */

    @Test
    void selectWrapper18() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) avgAge","min(age) minAge","max(age) maxAge").groupBy("manager_id").having("max(age) > {0}",30);
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);    }

    @Test
    void selectWrapper17() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","芮朋");
        //queryWrapper.select("email");
        User user = userMapper.selectOne(queryWrapper);
       System.out.println(user);
    }


    @Test
    void selectWrapper16() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.select("name").like("name","雨");
        queryWrapper.select("email");
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }



    @Test
    void selectWrapper15() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name").like("name","雨");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }


    /**
     * allEq
     */
    @Test
    void selectWrapper14() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("name","王");
//        map.put("age",null);
//
//        queryWrapper.allEq(map,false);
        Map<String,Object> map = new HashMap<>();
        map.put("name","雨");
        map.put("age",null);

        queryWrapper.allEq(StringUtils.isNotEmpty("zhangsan"),(k,v)->k.equals("name"),map,false);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    void selectWrapper13() {
        String name = "王";
        String email = null;
        int age = 24;
        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setAge(age);
        select13(user);
    }


    void select13(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper12() {
        String name = "王";
        String email = "";
        select12(name,email);
    }


    void select12(String name,String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),"name",name).like(StringUtils.isNotEmpty(email),"email",email);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }



    /**
     * 第一种情况：select id,name
     * from user
     * where name like '%雨%' and age<40
     * 第二种情况：select id,name,age,email
     * from user
     * where name like '%雨%' and age<40
     */


    @Test
    void selectWrapper11() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class,p -> !p.getColumn().equals("id") && !p.getColumn().equals("age")).like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    void selectWrapper10() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 4、创建日期为2019年2月14日并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     * 5、名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     * 6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age>20 and email is not null)
     * 7、（年龄小于40或邮箱不为空）并且名字为王姓
     * (age<40 or email is not null) and name like '王%'
     * 8、年龄为30、31、34、35
     * age in (30、31、34、35)
     * 9、只返回满足条件的其中一条语句即可
     * limit 1
     */


    @Test
    void selectWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("id").last("limit 2");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("age", Arrays.asList(30, 31, 32, 33));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        //queryWrapper.apply("age < {0} or email is not null",40).and(wq->wq.likeRight("name","王"));
        queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        queryWrapper.likeLeft("name", "王").or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14").inSql("manager_id", "select id from mp_user where name like '王%'");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 1、名字中包含雨并且年龄小于40
     * name like '%雨%' and age<40
     * 2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     * 3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=25 order by age desc,id asc
     */

    @Test
    void selectWrapper3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").or().gt("age", 25).orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").between("age", 31, 32).isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void selectWrapper1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper1 = Wrappers.query();
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 根据Map查询
     */
    @Test
    void selectByMap() {

        //map中的key为表中的字段
        Map<String, Object> map = new HashMap<>();
        map.put("age", 31);
        //map.put("name","张雨琪");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }


    /**
     * 根据ids查询
     */
    @Test
    void selectByIds() {
        List<Long> ids = Arrays.asList(1094592041087729666L, 1094590409767661570L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    /**
     * 根据id查询
     */
    @Test
    void selectById() {
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
    }


    /**
     * 查询所有
     */
    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);

        System.out.println("-------");
        users.forEach(System.out::println);
    }

    /**
     * 插入user
     */
    @Test
    void insert() {
        User user = new User();
        user.setUserName("夏天1");
        user.setAge(35);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setDesc("这是一个描述信息");
        int insert = userMapper.insert(user);
        System.out.println("insert--->" + insert);


    }
}