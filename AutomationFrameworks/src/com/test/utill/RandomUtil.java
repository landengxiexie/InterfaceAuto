package com.test.utill;

/**
 * @author d
 * @date 2018/4/25 002518:31
 */
public class RandomUtil {
    /**
     * 返回一个 0~conut（包含 count）的随机数
     *
     * @ count
     * @ return
     */
    public int getRandom(int count) {
//  round:四舍五入; floor:向下取整 ;ceil:向上取整
        return (int) Math.round(Math.random() * (count));
    }

    private String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 从 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 中随机
     * 生成长度为 length 的字符串
     *
     * @param length
     * @return
     */
    public String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(this.getRandom(len - 1)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        /*RandomUtil ru = new RandomUtil();
        for (int i = 0; i < 10; i++)
            System.out.println(ru.getRandomString(7));*/
    }
}