package com.wilson.util;

public class StringUtilByZhouyuan {
    public static boolean isNil(String value) {

        return (null == value || value.trim().equals(""));
    }


    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * @Title: getBeforZhimu
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param distace
     * @param distace2
     * @return
     * @author zlf
     * @date 2016-4-14 下午03:54:01
     * @version V1.0
     */
    public static String getBeforZhimu(String st, String charName) {
        if (st.indexOf(charName) > 0) {
            st = st.substring(0, st.indexOf("."));
        }
        return st;
    }

}
