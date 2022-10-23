package com.yudh.execute.constant;

/**
 * @author yudh
 * @date 2022-03-19 18:13
 */
public class Zip4jConstants {


    // 压缩方式

    /**
     * 仅打包，不压缩
     */
    public final static int COMP_STORE = 0;
    /**
     * 默认
     */
    public final static int COMP_DEFLATE = 8;
    /**
     * 加密压缩
     */
    public final static int COMP_AES_ENC = 99;

    // 压缩级别

    /**
     * 速度最快，压缩比最小
      */
    public final static int DEFLATE_LEVEL_FASTEST = 1;
    /**
     * 速度快，压缩比小
     */
    public final static int DEFLATE_LEVEL_FAST = 3;
    /**
     * 一般
     */
    public final static int DEFLATE_LEVEL_NORMAL = 5;
    public final static int DEFLATE_LEVEL_MAXIMUM = 7;
    public final static int DEFLATE_LEVEL_ULTRA = 9;
}
