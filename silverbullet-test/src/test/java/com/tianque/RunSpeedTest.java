package com.tianque;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by QQ on 2018/3/27.
 */
public class RunSpeedTest {
    @Test
    public void TestWEI(){
        int k = 1,j = 1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long start = System.nanoTime(); // 记录起始时间
        for (int i = 1; i < 1000000000; i++) {
            k = k*2^i;
        }
        long end = System.nanoTime();     // 记录结束时间
        System.out.println("普通运算：" + (end-start));              // 相减得出运行时间


        long start1 = System.nanoTime(); // 记录起始时间
        for (int i = 1; i < 1000000000; i++) {
            j = j << i;
        }
        long end1 = System.nanoTime();     // 记录结束时间
        System.out.println("位运算  ：" + (end1-start1));              // 相减得出运行时间
    }

    @Test
    public void TestStream(){
        List<Integer>  nums = new ArrayList<>();

        List<Integer>  nums1 = new ArrayList<>();
        List<Integer>  nums2 = new ArrayList<>();
        List<Integer>  nums3 = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            nums.add(i);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long start = System.nanoTime(); // 记录起始时间
        for (Integer num : nums) {
            if(num % 2 == 0){
                nums1.add(num);
            }
        }
        long end = System.nanoTime();     // 记录结束时间
        System.out.println("foreach循环：" + (end-start)+"，结果数量是"+nums1.size());              // 相减得出运行时间

        long start1 = System.nanoTime(); // 记录起始时间
        nums2 = nums.stream().filter((x) -> {
            return x % 2 == 0;
        }).collect(Collectors.toList());
        long end1 = System.nanoTime();     // 记录结束时间
        System.out.println("stream循环：" + (end1-start1)+"，结果数量是"+nums2.size());              // 相减得出运行时间


        long start2 = System.nanoTime(); // 记录起始时间
        nums3 = nums.parallelStream().filter((x) -> {
            return x % 2 == 0;
        }).collect(Collectors.toList());
        long end2 = System.nanoTime();     // 记录结束时间
        System.out.println("parallelStream循环：" + (end2-start2)+"，结果数量是"+nums3.size());              // 相减得出运行时间

    }



    @Test
    public void TestHashMapResize(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long start = System.nanoTime(); // 记录起始时间
        HashMap hashMap1 = new HashMap();
        for (int i = 0; i < 100000; i++) {
            hashMap1.put(i,i);
        }
        long end = System.nanoTime();     // 记录结束时间
        System.out.println("未设置HashMap的初始容量前：" + (end-start));              // 相减得出运行时间


        long start1 = System.nanoTime(); // 记录起始时间
        HashMap hashMap2 = new HashMap(150000,0.75f);
        for (int i = 0; i < 100000; i++) {
            hashMap2.put(i,i);
        }
        long end1 = System.nanoTime();     // 记录结束时间
        System.out.println("设置了HashMap的初始容量后  ：" + (end1-start1));              // 相减得出运行时间
    }

    interface StringFactory<T>{
        String create(T t);
    }

    @Test
    public void TestString(){
        StringFactory<String> sf = String::new;
        System.out.println(sf.create("12"));
    }



}
